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
 * Delete patient card servlet controller.
 * 
 * @author R.Mirzoiev
 * 
 */
@WebServlet("/admin/deleteCard")
public class DeleteCard extends HttpServlet {
   
	private static final long serialVersionUID = 2423353585955164816L;
	private static final Logger LOG = Logger.getLogger(DeleteCard.class);
    private CardService cardService = new CardService();
    private UtilServlet utilServlet = new UtilServlet();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	    	
        req.setAttribute("action", "/admin/deleteCard");
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
        try {
			if (!cardService.deleteCard(patientCards)) {
			    LOG.error("Can't update hospital card: " + patientCards);
			    req.setAttribute("error", "Can't delete hospital card!");
			    req.getRequestDispatcher("/errorPage.jsp").forward(req, resp);
			    return;
			} else {
			    LOG.info("Hospital card was successfully deleted");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
        
        req.setAttribute("patientCards", patientCards);
        try {
			utilServlet.setRequestListForPatientCard(req);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
        //go to rederect
        resp.sendRedirect(getServletContext().getContextPath() + "/admin/patientCards");
    }
}
