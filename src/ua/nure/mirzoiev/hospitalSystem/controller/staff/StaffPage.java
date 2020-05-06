package ua.nure.mirzoiev.hospitalSystem.controller.staff;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.nure.mirzoiev.hospitalSystem.entity.PatientCard;
import ua.nure.mirzoiev.hospitalSystem.tag.CardService;

import java.io.IOException;
import java.util.List;

@WebServlet("/staff/patients")
public class StaffPage extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CardService cardService = new CardService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	int docId = (int) req.getSession().getAttribute("id");
        List<PatientCard> patientCards = null;
		try {
			patientCards = cardService.findAllHospitalCardsByDoctorId(docId);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
        req.setAttribute("patientCards", patientCards);
        req.getRequestDispatcher("/WEB-INF/allHospitalCards.jsp").forward(req, resp);
    }
}
