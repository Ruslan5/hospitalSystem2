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
 * Update patient card servlet controller.
 * 
 * @author R.Mirzoiev
 * 
 */
@WebServlet("/admin/updateCard")
public class UpdateCard extends HttpServlet {

	private static final long serialVersionUID = 2496353585955164821L;
	private static final Logger LOG = Logger.getLogger(UpdateCard.class);
	private CardService cardService = new CardService();
	private UtilServlet utilServlet = new UtilServlet();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("action", "/admin/updateCard");
		int id = Integer.parseInt(req.getParameter("id"));
		PatientCard patientCards = null;
		try {
			patientCards = cardService.findCardById(id);
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		if (patientCards == null) {
			LOG.error("Cant' find hospital card with id: " + id);
			req.setAttribute("error", "Can't find hospital card!");
			req.getRequestDispatcher("/errorPage.jsp").forward(req, resp);
			return;
		}
		req.setAttribute("patientCards", patientCards);
		try {
			utilServlet.setRequestListForPatientCard(req);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		//go to forward
		req.getRequestDispatcher("/addNewHospitalCard.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PatientCard patientCards = null;
		try {
			patientCards = utilServlet.setPatientCard(req);
			System.out.println("patientCard doPost: " + patientCards);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			if (!cardService.updateHospitalCard(patientCards)) {
				LOG.error("Can't update patient card: " + patientCards);
				req.setAttribute("error", "Can't update patient card!");
				req.getRequestDispatcher("/errorPage.jsp").forward(req, resp);
				return;
			} else {
				LOG.info("Patient card updated!");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		//go to redirect
		resp.sendRedirect(getServletContext().getContextPath() + "/admin/page");
	}
}
