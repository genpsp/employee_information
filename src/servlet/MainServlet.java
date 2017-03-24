package servlet;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import model.CSVInOut;
import model.CityList;
import model.Department;
import model.Employee;
import model.Image;
import model.Position;
import model.TypeOfSearch;
import model.dao.DepartmentDAO;
import model.dao.EmployeeDAO;
import model.dao.ImageDAO;
import model.dao.PositionDAO;

/**
 * Servlet implementation class MainServlet
 */
@WebServlet("/MainServlet")
@MultipartConfig()
public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		String requestType = request.getParameter("requestType");
		EmployeeDAO empDao = new EmployeeDAO();
		DepartmentDAO departDao = new DepartmentDAO();
		PositionDAO positionDao = new PositionDAO();
		ImageDAO imgDao = new ImageDAO();

		if (requestType.equals("詳細")) {
			String empID = request.getParameter("empID");
			Employee employee = empDao.getEmployee(empID);
			session.setAttribute("employee", employee);

			RequestDispatcher rdisp = request.getRequestDispatcher("/WEB-INF/jsp/employeeDiscription.jsp");
			rdisp.forward(request, response);
		} else if (requestType.equals("編集")) {
			request.setAttribute("departmentList", departDao.findAll());
			String empID = request.getParameter("empID");
			Employee employee = empDao.getEmployee(empID);
			session.setAttribute("employee", employee);
			request.setAttribute("cityList", CityList.getCityList());

			RequestDispatcher rdisp = request.getRequestDispatcher("/WEB-INF/jsp/employeeEditor.jsp");
			rdisp.forward(request, response);
		} else if (requestType.equals("削除")) {
			String empID = request.getParameter("empID");
			boolean result = empDao.deleteEmployee(empID);

			if (result == false) {
				String errorMsg = "<p>削除できませんでした</p>";
				request.setAttribute("errorMsg", errorMsg);
			}
			session.setAttribute("employeeList", empDao.searchEmployee());

			RequestDispatcher rdisp = request.getRequestDispatcher("/WEB-INF/jsp/employee.jsp");
			rdisp.forward(request, response);
		} else if (requestType.equals("新規追加")) {
			session.removeAttribute("employee");
			request.setAttribute("departmentList", departDao.findAll());
			request.setAttribute("positionList", positionDao.findAll());
			request.setAttribute("cityList", CityList.getCityList());

			RequestDispatcher rdisp = request.getRequestDispatcher("/WEB-INF/jsp/employeeEditor.jsp");
			rdisp.forward(request, response);
		} else if (requestType.equals("検索...")) {
			request.setAttribute("departmentList", departDao.findAll());

			RequestDispatcher rdisp = request.getRequestDispatcher("/WEB-INF/jsp/employeeSearch.jsp");
			rdisp.forward(request, response);
		} else if (requestType.equals("検索")) {
			String department = request.getParameter("department");
			String empID = request.getParameter("empID");
			String searchString = request.getParameter("searchString");

			TypeOfSearch type = new TypeOfSearch(department, empID, searchString);
			List<Employee> employeeList = empDao.searchEmployee(type);

			if (employeeList.size() == 0) {
				RequestDispatcher rdisp = request.getRequestDispatcher("/WEB-INF/jsp/employeeNotFound.jsp");
				rdisp.forward(request, response);
			}
			session.setAttribute("employeeList", employeeList);

			RequestDispatcher rdisp = request.getRequestDispatcher("/WEB-INF/jsp/employee.jsp");
			rdisp.forward(request, response);
		} else if (requestType.equals("CSVファイルに出力")) {
			@SuppressWarnings("unchecked")
			List<Employee> employeeList = (List<Employee>) session.getAttribute("employeeList");

			CSVInOut csv = new CSVInOut();
			List<Employee> resultList = csv.searchEmployee(employeeList);

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			OutputStreamWriter out = new OutputStreamWriter(baos);
			BufferedWriter bw = new BufferedWriter(out);

			bw.write("ID,名前,年齢,性別,画像ID,郵便番号,都道府県,住所,部署ID,入社日,退社日\n");

			for (Employee emp : resultList) {
				bw.write(emp.getEmpID() + ",");
				bw.write(emp.getName() + ",");
				bw.write(emp.getAge() + ",");
				if (emp.getSex() == 0) {
					bw.write("男" + ",");
				} else {
					bw.write("女" + ",");
				}
				bw.write(emp.getImage().getImageID() + ",");
				bw.write(emp.getAddressNum() + ",");
				bw.write(emp.getCity() + ",");
				bw.write(emp.getAddress() + ",");
				bw.write(emp.getDepartment().getId() + ",");
				bw.write(emp.getEnterDate() + ",");
				bw.write(emp.getRetireDate() + "\n");
			}
			bw.flush();
			bw.close();

			response.setContentType("application/octet-stream; charset=UTF-8");
			response.setHeader("Content-Disposition", "attachment; filename=\"search_result.csv\"");
			response.setContentLength(baos.size());

			ServletOutputStream os = response.getOutputStream();
			os.write(baos.toByteArray());
			os.close();

		} else if (requestType.equals("戻る")) {
			session.setAttribute("employeeList", empDao.searchEmployee());

			RequestDispatcher rdisp = request.getRequestDispatcher("/WEB-INF/jsp/employee.jsp");
			rdisp.forward(request, response);
		} else if (requestType.equals("TOPへ")) {
			RequestDispatcher rdisp = request.getRequestDispatcher("index.jsp");
			rdisp.forward(request, response);
		} else if (requestType.equals("getImage")) {
			Employee employee = (Employee) session.getAttribute("employee");

			byte[] imgBytes = imgDao.getImage(employee.getImage().getImageID());

			response.setContentType("image/jpg");
			ServletOutputStream out = response.getOutputStream();
			out.write(imgBytes, 0, imgBytes.length);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		String requestType = request.getParameter("requestType");
		EmployeeDAO empDao = new EmployeeDAO();
		PositionDAO positionDao = new PositionDAO();
		DepartmentDAO departDao = new DepartmentDAO();

		if (requestType.equals("キャンセル")) {
			session.setAttribute("employeeList", empDao.searchEmployee());
			session.removeAttribute("employee");

			RequestDispatcher rdisp = request.getRequestDispatcher("/WEB-INF/jsp/employee.jsp");
			rdisp.forward(request, response);
		} else if (requestType.equals("設定")) {

			String empID = request.getParameter("empID");
			String name = request.getParameter("name");
			String age = request.getParameter("age");
			String sex = request.getParameter("sex");
			String addressNum = request.getParameter("addressNum");
			String city = request.getParameter("city");
			String address = request.getParameter("address");
			String departID = request.getParameter("department");
			Department department = departDao.searchDepartment(departID);
			String positionID = request.getParameter("position");
			Position position = positionDao.searchPosition(positionID);
			String enterDate = request.getParameter("enterDate");
			String retireDate = request.getParameter("retireDate");

			Part filePart = request.getPart("file");
			Image image = new Image();
			image.setData(filePart.getInputStream());
			image.setImageID(ImageDAO.getLastIDNum());

			Employee emp = new Employee(empID, name, age, Integer.parseInt(sex), image, addressNum, city, address,
					department, position, enterDate, retireDate);
			boolean result = empDao.addEmployee(emp);

			String resultMsg = "<p>データベースの更新が完了しました</p>";

			if (result == false) {
				resultMsg = "<p>データベースの更新に失敗しました</p>";
				request.setAttribute("resultMsg", resultMsg);
				request.setAttribute("type", "MainServlet");

				RequestDispatcher rdisp = request.getRequestDispatcher("/WEB-INF/jsp/result.jsp");
				rdisp.forward(request, response);
			}

			session.setAttribute("employeeList", empDao.searchEmployee());
			request.setAttribute("resultMsg", resultMsg);
			request.setAttribute("type", "MainServlet");

			RequestDispatcher rdisp = request.getRequestDispatcher("/WEB-INF/jsp/result.jsp");
			rdisp.forward(request, response);

		} else if (requestType.equals("更新")) {

			String empID = request.getParameter("empID");
			String name = request.getParameter("name");
			String age = request.getParameter("age");
			String sex = request.getParameter("sex");
			String addressNum = request.getParameter("addressNum");
			String city = request.getParameter("city");
			String address = request.getParameter("address");
			String departID = request.getParameter("department");
			Department department = departDao.searchDepartment(departID);
			String positionID = request.getParameter("position");
			Position position = positionDao.searchPosition(positionID);
			String enterDate = request.getParameter("enterDate");
			String retireDate = request.getParameter("retireDate");

			Part filePart = request.getPart("file");
			Image image = new Image();
			Employee employee = (Employee) session.getAttribute("employee");

			if (filePart.getSize() == 0) {
				image = employee.getImage();
			} else {
				image.setData(filePart.getInputStream());
				image.setImageID(employee.getImage().getImageID());
			}
			Employee emp = new Employee(empID, name, age, Integer.parseInt(sex), image, addressNum, city, address,
					department, position, enterDate, retireDate);
			boolean result = empDao.updateEmployee(emp);

			String resultMsg = "<p>データベースの更新が完了しました</p>";

			if (result == false) {
				resultMsg = "<p>データベースの更新に失敗しました</p>";
				request.setAttribute("resultMsg", resultMsg);
				request.setAttribute("type", "MainServlet");

				RequestDispatcher rdisp = request.getRequestDispatcher("/WEB-INF/jsp/result.jsp");
				rdisp.forward(request, response);
			}

			session.setAttribute("employeeList", empDao.searchEmployee());
			request.setAttribute("resultMsg", resultMsg);
			request.setAttribute("type", "MainServlet");

			RequestDispatcher rdisp = request.getRequestDispatcher("/WEB-INF/jsp/result.jsp");
			rdisp.forward(request, response);
		}

	}

}
