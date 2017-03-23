package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Department;
import model.dao.DepartmentDAO;

/**
 * Servlet implementation class DepartmentServlet
 */
@WebServlet("/DepartmentServlet")
public class DepartmentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DepartmentServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		String requestType = request.getParameter("requestType");
		DepartmentDAO departDao = new DepartmentDAO();

		if (requestType.equals("戻る")) {
			session.setAttribute("departmentList", departDao.findAll());

			RequestDispatcher rdisp = request.getRequestDispatcher("/WEB-INF/jsp/department.jsp");
			rdisp.forward(request, response);
		} else if (requestType.equals("編集")) {
			String id = request.getParameter("departID");

			session.setAttribute("id", id);
			request.setAttribute("depart", departDao.searchDepartment(id));

			RequestDispatcher rdisp = request.getRequestDispatcher("/WEB-INF/jsp/departmentEditor.jsp");
			rdisp.forward(request, response);
		} else if (requestType.equals("削除")) {
			String id = request.getParameter("departID");
			boolean result = departDao.deleteDepartment(id);

			if (result == false) {
				String errorMsg = "<p>削除できませんでした</p>";
				request.setAttribute("errorMsg", errorMsg);
			}
			session.setAttribute("departmentList", departDao.findAll());

			RequestDispatcher rdisp = request.getRequestDispatcher("/WEB-INF/jsp/department.jsp");
			rdisp.forward(request, response);
		} else if (requestType.equals("新規追加")) {
			RequestDispatcher rdisp = request.getRequestDispatcher("/WEB-INF/jsp/departmentEditor.jsp");
			rdisp.forward(request, response);
		} else if (requestType.equals("TOPへ")) {
			RequestDispatcher rdisp = request.getRequestDispatcher("index.jsp");
			rdisp.forward(request, response);
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
		DepartmentDAO departDao = new DepartmentDAO();

		if (requestType.equals("設定")) {
			String depart = request.getParameter("department");
			String id = departDao.getLastIDNum();

			Department department = new Department(id, depart);
			boolean result = departDao.addDepartment(department);

			String resultMsg = "<p>データベースの更新が完了しました</p>";

			if (result == false) {
				resultMsg = "<p>データベースの更新に失敗しました</p>";
				request.setAttribute("resultMsg", resultMsg);
				request.setAttribute("type", "DepartmentServlet");

				RequestDispatcher rdisp = request.getRequestDispatcher("/WEB-INF/jsp/result.jsp");
				rdisp.forward(request, response);
			}

			session.setAttribute("departmentList", departDao.findAll());
			request.setAttribute("resultMsg", resultMsg);
			request.setAttribute("type", "DepartmentServlet");

			RequestDispatcher rdisp = request.getRequestDispatcher("/WEB-INF/jsp/result.jsp");
			rdisp.forward(request, response);

		} else if (requestType.equals("更新")) {
			String id = (String) session.getAttribute("id");
			session.removeAttribute("id");
			String depart = request.getParameter("department");
			Department department = new Department(id, depart);

			boolean result = departDao.updateDepartment(department);
			String resultMsg = "<p>データベースの更新が完了しました</p>";

			if (result == false) {
				resultMsg = "<p>データベースの更新に失敗しました</p>";
				request.setAttribute("resultMsg", resultMsg);
				request.setAttribute("type", "DepartmentServlet");

				RequestDispatcher rdisp = request.getRequestDispatcher("/WEB-INF/jsp/result.jsp");
				rdisp.forward(request, response);
			}

			session.setAttribute("departmentList", departDao.findAll());
			request.setAttribute("resultMsg", resultMsg);
			request.setAttribute("type", "DepartmentServlet");

			RequestDispatcher rdisp = request.getRequestDispatcher("/WEB-INF/jsp/result.jsp");
			rdisp.forward(request, response);
		} else if (requestType.equals("キャンセル")) {
			session.setAttribute("departmentList", departDao.findAll());

			RequestDispatcher rdisp = request.getRequestDispatcher("/WEB-INF/jsp/department.jsp");
			rdisp.forward(request, response);
		}

	}

}
