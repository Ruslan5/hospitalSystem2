package ua.nure.mirzoiev.hospitalSystem.controller.staff;

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

@WebServlet("/staff/updateCard")
public class UpdateCard extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(UpdateCard.class);
    private CardService cardService = new CardService();
    private UtilServlet utilServlet = new UtilServlet();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("action", "/staff/updateCard");
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
        req.getRequestDispatcher("/addNewHospitalCard.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PatientCard patientCards = null;
		try {
			patientCards = utilServlet.setPatientCard(req);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
        try {
			if (!cardService.updateCard(patientCards)) {
			    LOG.error("Can't update hospital card: " + patientCards);
			    req.setAttribute("error", "Can't update hospital card!");
			    req.getRequestDispatcher("/errorPage.jsp").forward(req, resp);
			    return;
			} else {
			    LOG.info("Hospital card was successfully updated");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
        resp.sendRedirect(getServletContext().getContextPath() + "/staff/patients");
    }
}
