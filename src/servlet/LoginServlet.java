package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.User;
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
		UserDAO userDao = new UserDAO();

		if (requestType.equals("登録")) {
			String id = userDao.getLastIDNum();
			String name = request.getParameter("name");
			String pass = request.getParameter("pass");
			String mailAddress = request.getParameter("mail");

			User user = new User(id, name, pass, mailAddress);

			boolean result = userDao.addUser(user);

			String resultMsg = "登録しました";
			request.setAttribute("resultMsg", resultMsg);

			RequestDispatcher rdisp = request.getRequestDispatcher("/WEB-INF/jsp/result.jsp");
			rdisp.forward(request, response);
		}

	}

}
