package ua.nure.mirzoiev.hospitalSystem.controller.patient;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.nure.mirzoiev.hospitalSystem.db.UtilServlet;
import ua.nure.mirzoiev.hospitalSystem.entity.Person;
import ua.nure.mirzoiev.hospitalSystem.entity.Role;

import java.io.IOException;
import java.util.List;

@WebServlet("/patient/doctors")
public class PatientPage extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final UtilServlet utilServlet = new UtilServlet();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Person> clientsList = null;
		try {
			clientsList = utilServlet.getAllByRole(Role.DOCTOR);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
        req.setAttribute("clientsList", clientsList);
        req.setAttribute("clientGetRole", Role.DOCTOR.getName());
        req.setAttribute("action", "/admin/sortClients");

        req.getRequestDispatcher("/WEB-INF/doctors.jsp").forward(req, resp);
    }
}
