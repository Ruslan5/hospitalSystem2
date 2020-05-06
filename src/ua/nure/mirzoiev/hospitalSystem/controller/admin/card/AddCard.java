package ua.nure.mirzoiev.hospitalSystem.controller.admin.card;

import org.apache.log4j.Logger;

import ua.nure.mirzoiev.hospitalSystem.db.UtilServlet;
import ua.nure.mirzoiev.hospitalSystem.entity.PatientCard;
import ua.nure.mirzoiev.hospitalSystem.tag.CardService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Add patient card for "ADMIN" role servlet controller.
 * 
 * @author R.Mirzoiev
 * 
 */
@WebServlet("/admin/addCard")
public class AddCard extends HttpServlet {

	private static final long serialVersionUID = 1953979587369586974L;
	private static final Logger LOG = Logger.getLogger(AddCard.class);
	private CardService сardService = new CardService();
	private UtilServlet utilServlet = new UtilServlet();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			req.setAttribute("action", "/admin/addCard");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			utilServlet.setRequestListForPatientCard(req);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		req.getRequestDispatcher("/addNewHospitalCard.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PatientCard patientCards = null;
		try {
			patientCards = utilServlet.setPatientCard(req);
			System.out.println("patientCard doPost: " + patientCards);
		} catch (NullPointerException e) {
			LOG.error("Can't set hospital card. Error: " + e);
			req.setAttribute("error", "An error occurred. Please, try again");
			req.getRequestDispatcher("/errorPage.jsp").forward(req, resp);
			return;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			if (!сardService.insertHospitalCard(patientCards)) {
				LOG.error("Can't insert hospital card: " + patientCards);
				req.setAttribute("error", "Can't insert hospital card!");
				req.getRequestDispatcher("/errorPage.jsp").forward(req, resp);
				return;
			} else {
				LOG.info("Hospital card was successfully inserted");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		resp.sendRedirect(getServletContext().getContextPath() + "/admin/patientCards");
	}
}
