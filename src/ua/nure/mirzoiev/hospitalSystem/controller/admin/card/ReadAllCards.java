package ua.nure.mirzoiev.hospitalSystem.controller.admin.card;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.nure.mirzoiev.hospitalSystem.entity.PatientCard;
import ua.nure.mirzoiev.hospitalSystem.tag.CardService;

import java.io.IOException;
import java.util.List;

/**
 * Read all patient card servlet controller.
 * 
 * @author R.Mirzoiev
 * 
 */
@WebServlet("/admin/patientCards")
public class ReadAllCards extends HttpServlet {
	
	private static final long serialVersionUID = 5323359585955164843L;
	private static final CardService cardService = new CardService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<PatientCard> patientCards = null;
		try {
			patientCards = cardService.getAllCards();
			System.out.println("doGet: " + patientCards);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		//set attribute
		req.setAttribute("patientCards", patientCards);
		//go to forward
		req.getRequestDispatcher("/WEB-INF/allHospitalCards.jsp").forward(req, resp);
	}
}
