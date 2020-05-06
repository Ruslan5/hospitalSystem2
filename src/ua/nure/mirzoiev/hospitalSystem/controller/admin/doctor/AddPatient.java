package ua.nure.mirzoiev.hospitalSystem.controller.admin.doctor;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.mirzoiev.hospitalSystem.db.UtilServlet;
import ua.nure.mirzoiev.hospitalSystem.entity.Person;

@WebServlet("/admin/AddPatient")
public class AddPatient extends HttpServlet { 
	private static final Logger LOG = Logger.getLogger(AddPatient.class);
    private UtilServlet utilServlet = new UtilServlet();
	
    protected void doGet(HttpServletRequest req, HttpServletResponse response) 
        throws ServletException, IOException {
    	req.setAttribute("action", "/admin/AddPatient");
        UtilServlet.setRequestListForClient(req);

        req.getRequestDispatcher("/WEB-INF/createPatient.jsp").forward(req, response);
 
    }
     
    protected void doPost(HttpServletRequest req, HttpServletResponse response) 
        throws ServletException, IOException {
    	Person person = null;
		try {
			person = UtilServlet.setPerson(req);
			System.out.println("client from doPost: " + person);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
        try {
			if (!utilServlet.insertPerson(person)) {
			    req.setAttribute("error", "Can't insert user! User may already be registered!");
			    LOG.error("Can't insert client: " + person);
			    req.getRequestDispatcher("/errorPage.jsp").forward(req, response);
			    return;
			} else {
			    LOG.info("Person was successfully inserted");

			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
        response.sendRedirect(getServletContext().getContextPath() + "/admin/page");
    }
}