package ua.nure.mirzoiev.hospitalSystem.controller;

import org.apache.log4j.Logger;

import ua.nure.mirzoiev.hospitalSystem.db.UtilServlet;
import ua.nure.mirzoiev.hospitalSystem.entity.Person;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Login servlet controller.
 * 
 * @author R.Mirzoiev
 * 
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 7529409572606319672L;
	private static final Logger LOG = Logger.getLogger(LoginServlet.class);
	private UtilServlet utilServlet = new UtilServlet();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/login.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String login = req.getParameter("login");
		String password = req.getParameter("password");
		Person person = null;
		try {
			person = utilServlet.getPearsontByLoginAndPassword(login, password);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Введено: " + login + " " + password);
		if (person == null) {
			LOG.error("Can't find user with such combination of login and password. Login: " + login);
			req.setAttribute("error", "Can't find user with such login and password!");
			req.getRequestDispatcher("/errorPage.jsp").forward(req, resp);
			return;
		} else {
			LOG.info("Person: " + login + " successfully logged in");
		}

		HttpSession session = req.getSession();
		String role = person.getRole().getName();
		session.setAttribute("id", person.getId());
		session.setAttribute("role", role);
		session.setAttribute("login", person.getLogin());
		session.setMaxInactiveInterval(15 * 60);

		if (role == null) {
			resp.sendRedirect(getServletContext().getContextPath() + "/admin/page");
		}
		if (role.equals("admin")) {
			resp.sendRedirect(getServletContext().getContextPath() + "/adminPage.jsp");
		} else if (role.equals("doctor") || role.equals("nurse")) {
			resp.sendRedirect(getServletContext().getContextPath() + "/staff/patients");
		} else if (role.equals("patient")) {
			resp.sendRedirect(getServletContext().getContextPath() + "/patient/doctors");
		}
	}
}
