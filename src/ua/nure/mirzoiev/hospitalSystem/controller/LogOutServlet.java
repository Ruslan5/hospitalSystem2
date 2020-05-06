package ua.nure.mirzoiev.hospitalSystem.controller;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * Logout servlet controller.
 * 
 * @author R.Mirzoiev
 * 
 */
@WebServlet("/logout")
public class LogOutServlet extends HttpServlet {

	private static final long serialVersionUID = 3975536535957692967L;
	private static final Logger LOG = Logger.getLogger(LogOutServlet.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Cookie[] cookies = req.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("JSESSIONID")) {
					LOG.info("JSESSIONID: " + cookie.getValue());
					break;
				}
			}
		}

		HttpSession session = req.getSession(false);
		LOG.info("Person = " + session.getAttribute("login"));
		if (session != null) {
			session.invalidate();
			LOG.info("Person logged out");
		}

		resp.sendRedirect(getServletContext().getContextPath() + "/login");
	}
}
