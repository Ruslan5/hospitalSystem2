package ua.nure.mirzoiev.hospitalSystem.controller;

import org.apache.log4j.Logger;

import ua.nure.mirzoiev.hospitalSystem.db.UtilServlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/language")
public class LanguageServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(LanguageServlet.class);
	private UtilServlet utilServlet = new UtilServlet();
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
		
		request.setAttribute("action", "language?=");
		String url = request.getParameter("url");
		String lang = request.getParameter("language");
		
		System.out.println("url: " + url);
		System.out.println(request);

		LOG.trace("url: " + url + ", lang: " + lang);

		// set language
		
	
		request.getSession().setAttribute("lang", lang);
		System.out.println(lang);
		LOG.trace("Language has been changed to " + lang);

//		request.getRequestDispatcher(getServletContext().getContextPath() + url).forward(request, resp);
		System.out.println(url);
		System.out.println(request);
		resp.sendRedirect(getServletContext().getContextPath() + "/adminPage.jsp");
	}
}
