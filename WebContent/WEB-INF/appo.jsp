<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<c:set var="title" value="Create person" />

<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body>

<div class="table-center-buttons">
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<table border="1">
<caption>Admin</caption>
<tr>
<!--  <th>id</th>  -->
<th>patient_id</th>
<th>PatientSurname</th>
<th>doctor_id</th>
<th>DoctorSurname</th>
</tr>

	<c:forEach var="appointment" items="${appointment}">
<tr>
	<!--  <td>${appointment.id}</td> -->
	<td>${appointment.getPatient_Id()}</td>
	<td>${appointment.getPatient().getSurname()}</td>
	<td>${appointment.getDoctor().getId()}</td>
	<td>${appointment.getDoctor().getSurname()}</td>
</tr>
	</c:forEach>
</table>
</div>
<a href="http://localhost:9092/HospitalTest2/*">addDoctor</a>
<%@ include file="/WEB-INF/jspf/footer.jspf"%>

</body>

</html>