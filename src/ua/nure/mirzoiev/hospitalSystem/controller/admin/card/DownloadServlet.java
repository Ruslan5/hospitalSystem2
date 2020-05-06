package ua.nure.mirzoiev.hospitalSystem.controller.admin.card;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.PreparedStatement;

import ua.nure.mirzoiev.hospitalSystem.entity.PatientCard;
import ua.nure.mirzoiev.hospitalSystem.tag.CardService;

/**
 * Servlet implementation class DownloadServlet
 */
@WebServlet("/downloadServlet")
public class DownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CardService cardService = new CardService();
	private static final int BUFFER_SIZE = 4096;   
    
    // database connection settings
	private static String url = "jdbc:mysql://localhost/hospitalTest2?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
	private static String userName = "root";
	private static String password = "root";
	 private static final String FIND_CARD_BY_ID = "SELECT card.id, doc.id, doc.login, doc.password, doc.name,  doc.surname, doc_role.name role_name, doc.additional_info, " +
	            "pat.id, pat.login, pat.password, pat.name, pat.surname,  pat_role.name role_name, pat.additional_info, " +
	            "nurse.id, nurse.login, nurse.password, nurse.name, nurse.surname, nurse_role.name role_name, nurse.additional_info, " +
	            "card.statuses, card.diagnosis, card.final_diagnosis, card.drug, card.operations, card.procedures " +
	            "FROM patient_card card " +
	            "LEFT JOIN person doc ON card.doctor_id = doc.id " +
	            "LEFT JOIN person pat ON card.patient_id = pat.id " +
	            "LEFT JOIN person nurse ON card.nurse_id = nurse.id " +
	            "LEFT JOIN roles doc_role ON doc.role_id = doc_role.id " +
	            "LEFT JOIN roles pat_role ON pat.role_id = pat_role.id " +
	            "LEFT JOIN roles nurse_role ON nurse.role_id = nurse_role.id WHERE card.id=?";
	 
//    protected void doGet(HttpServletRequest request,
//            HttpServletResponse response) throws ServletException, IOException {
//    	int id = Integer.parseInt(request.getParameter("id"));
//    	try {
//			Class.forName("com.mysql.cj.jdbc.Driver");
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//        Connection connection = null;
//        PreparedStatement statement = null;
//        ResultSet resultSet = null;
//        PatientCard patientCard = new PatientCard();
//
//        try {
//            connection = DriverManager.getConnection(url, userName, password);
//            statement = connection.prepareStatement(FIND_CARD_BY_ID);
//            patientCard = cardService.downloadCardById(id);
//            statement.setInt(1, id);
//
//            // queries the database
// 
//            resultSet = statement.executeQuery();
//            if (resultSet.next()) {
//                // gets file name and file blob data
//                String fileName = resultSet.getString(patientCard.toString());
//                Blob blob = resultSet.getBlob(patientCard.toString());
//                InputStream inputStream = blob.getBinaryStream();
//                int fileLength = inputStream.available();
//                 
//                System.out.println("fileLength = " + fileLength);
// 
//                ServletContext context = getServletContext();
// 
//                // sets MIME type for the file download
//                String mimeType = context.getMimeType(fileName);
//                if (mimeType == null) {        
//                    mimeType = "application/octet-stream";
//                }              
//                 
//                // set content properties and header attributes for the response
//                response.setContentType(mimeType);
//                response.setContentLength(fileLength);
//                String headerKey = "Content-Disposition";
//                String headerValue = String.format("attachment; filename=\"%s\"", fileName);
//                response.setHeader(headerKey, headerValue);
// 
//                // writes the file to the client
//                OutputStream outStream = response.getOutputStream();
//                 
//                byte[] buffer = new byte[BUFFER_SIZE];
//                int bytesRead = -1;
//                 
//                while ((bytesRead = inputStream.read(buffer)) != -1) {
//                    outStream.write(buffer, 0, bytesRead);
//                }
//                 
//                inputStream.close();
//                outStream.close();             
//            } else {
//                // no file found
//                response.getWriter().print("File not found for the id: " + id);  
//            }
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//            response.getWriter().print("SQL Error: " + ex.getMessage());
//        } catch (IOException ex) {
//            ex.printStackTrace();
//            response.getWriter().print("IO Error: " + ex.getMessage());
//        } catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} finally {
//            if (connection != null) {
//                // closes the database connection
//                try {
//                    connection.close();
//                } catch (SQLException ex) {
//                    ex.printStackTrace();
//                }
//            }          
//        }
//    }
	 protected void doPost(HttpServletRequest request,
	            HttpServletResponse response) throws ServletException, IOException {
		 String patientName = request.getParameter("firstName");
			String patientLastName = request.getParameter("lastName");

			// to obtain the bytes for unsafe characters
			String fileName = URLEncoder.encode(patientName + patientLastName + ".txt", "UTF-8");
			String filePath = "C:/workspace_eclipse/SummaryTask4/WebContent/WEB-INF/DischangedPatients/" + patientName
					+ patientLastName + ".txt";

			response.setHeader("Content-disposition", "attachment; filename=" + fileName);
			File file = new File(filePath);

			// This should send the file to browser
			OutputStream out = response.getOutputStream();
			FileInputStream in = new FileInputStream(file);
			byte[] buffer = new byte[4096];
			int length;
			while ((length = in.read(buffer)) > 0) {
				out.write(buffer, 0, length);
			}
			in.close();
			out.flush();
			
			request.getRequestDispatcher(getServletContext().getContextPath() + url).forward(request, response);

	 }
    
}
