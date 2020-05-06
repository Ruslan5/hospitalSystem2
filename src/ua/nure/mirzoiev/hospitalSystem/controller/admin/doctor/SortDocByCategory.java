package ua.nure.mirzoiev.hospitalSystem.controller.admin.doctor;

import ua.nure.mirzoiev.hospitalSystem.db.UtilServlet;
import ua.nure.mirzoiev.hospitalSystem.entity.Person;
import ua.nure.mirzoiev.hospitalSystem.entity.Role;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@WebServlet("/admin/SortDocByCategory")
public class SortDocByCategory extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final UtilServlet clientService = new UtilServlet();
    
    Comparator<Person> byCategory = 
    		(Person o1, Person o2)->o1.getAdditionalInfo().compareTo(o2.getAdditionalInfo());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Role role = Role.getRoleByName(req.getParameter("role"));

        List<Person> patientList = null;
		try {
			patientList = clientService.getAllByRole(role);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
        System.out.println(patientList);
        Collections.sort(patientList, byCategory);
        req.setAttribute("clientsList", patientList);
        req.setAttribute("role", role);
		req.getRequestDispatcher("/WEB-INF/doctors.jsp").forward(req, resp);
    }
}
