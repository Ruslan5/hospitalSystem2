package ua.nure.mirzoiev.hospitalSystem.controller.admin.doctor;

import org.apache.log4j.Logger;

import ua.nure.mirzoiev.hospitalSystem.db.UtilServlet;
import ua.nure.mirzoiev.hospitalSystem.entity.Person;
import ua.nure.mirzoiev.hospitalSystem.entity.Role;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin/deletePerson")
public class DeletePerson extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(DeletePerson.class);
	private UtilServlet utilServlet = new UtilServlet();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		req.setAttribute("action", "/admin/deletePerson");
		int id = Integer.parseInt(req.getParameter("id"));
		Person person = null;
		try {
			person = utilServlet.findPersondById(id);
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		if (person == null) {
			LOG.error("Cant' find hospital card with id: " + id);
			req.setAttribute("error", "Can't find hospital card!");
			req.getRequestDispatcher("/errorPage.jsp").forward(req, resp);
			return;
		}
		try {
			if (!utilServlet.deletePerson(person)) {
				LOG.error("Can't delete person: " + person);
				req.setAttribute("error", "Can't delete person!");
				req.getRequestDispatcher("/errorPage.jsp").forward(req, resp);
				return;
			} else {
				LOG.info("Person was successfully deleted");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		req.setAttribute("clientsList", person);
		try {
			utilServlet.setRequestListForPatientCard(req);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		resp.sendRedirect(getServletContext().getContextPath() + "/admin/page");
	}
}
