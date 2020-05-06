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

@WebServlet("/admin/sortByCount")
public class SortByCount extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final UtilServlet clientService = new UtilServlet();

	Comparator<Person> byCount = 
    		(Person o1, Person o2)->o1.getCount_patients() - o2.getCount_patients();

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
		//sort by patient count
		Collections.sort(patientList, byCount);
		req.setAttribute("clientsList", patientList);
		req.setAttribute("role", role);

		req.getRequestDispatcher("/WEB-INF/doctors.jsp").forward(req, resp);
		
	}
}
