package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Authority;
import model.User;
import model.dao.EmployeeDAO;
import model.dao.UserDAO;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String requestType = request.getParameter("requestType");
		HttpSession session = request.getSession();
		UserDAO userDao = new UserDAO();

		if (requestType.equals("新規")) {
			RequestDispatcher rdisp = request.getRequestDispatcher("/WEB-INF/jsp/newUser.jsp");
			rdisp.forward(request, response);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String requestType = request.getParameter("requestType");
		HttpSession session = request.getSession();
		UserDAO userDao = new UserDAO();
		EmployeeDAO empDao = new EmployeeDAO();

		if (requestType.equals("登録")) {
			String empID = request.getParameter("empID");
			String pass = request.getParameter("pass");

			User user = new User(empID, pass);

			boolean result = userDao.addUser(user);
			String resultMsg = "登録しました";

			if (!result) {
				resultMsg = "登録できませんでした";
			}
			request.setAttribute("resultMsg", resultMsg);

			RequestDispatcher rdisp = request.getRequestDispatcher("/WEB-INF/jsp/result.jsp");
			rdisp.forward(request, response);
		} else if (requestType.equals("ログイン")) {
			String empID = request.getParameter("empID");
			String pass = request.getParameter("pass");

			User user = new User(empID, pass);
			boolean result = userDao.confirmUser(user);

			if (result) {
				session.setAttribute("User", user);
				Authority authority = new Authority(empDao.getEmployee(user.getId()));
				session.setAttribute("authority", authority);

				RequestDispatcher rdisp = request.getRequestDispatcher("index.jsp");
				rdisp.forward(request, response);
			} else if (!result) {
				String loginMsg = "<p>ログイン情報が間違っているか、登録されていません</p>";
				request.setAttribute("loginMsg", loginMsg);

				RequestDispatcher rdisp = request.getRequestDispatcher("index.jsp");
				rdisp.forward(request, response);
			}
		}

	}

}
