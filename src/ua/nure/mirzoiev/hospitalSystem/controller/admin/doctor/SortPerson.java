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
import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@WebServlet("/admin/sortPerson")
public class SortPerson extends HttpServlet {
    private static final UtilServlet personService = new UtilServlet();
    
    Comparator<Person> byName = 
    		(Person o1, Person o2)->o1.getSurname().compareTo(o2.getSurname());
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Role role = Role.getRoleByName(req.getParameter("role"));
        System.out.println(role);
        List<Person> patientList = null;
		try {
			patientList = personService.getAllByRole(role);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
        System.out.println(patientList);
        Collections.sort(patientList, byName);
        req.setAttribute("clientsList", patientList);
        req.setAttribute("role", role);
		req.getRequestDispatcher("/WEB-INF/doctors.jsp").forward(req, resp);

    }
}
