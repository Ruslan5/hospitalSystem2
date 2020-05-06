<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<c:set var="title" value="patients"/>

<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body>

<div class="form text-center">	
	<br>
	
	<%@ include file="/WEB-INF/jspf/header.jspf" %>
	
<div class="table-center-buttons">
	<table class="table table-striped">
	<caption>Cards</caption>
	<tr>
	<th>DocName</th>
	<th>DocSurname</th>
	<th>DocadditionalInfo</th>
	
	<th>PatName</th>
	<th>PatSurname</th>
	<th>PatadditionalInfo</th>
	
	<th>NurseName</th>
	<th>NurseSurname</th>
	<th>NursePatadditionalInfo</th>
	
	<th>Status</th>
	
	<c:if test="${sessionScope.role =='doctor'}">
	<th>Diagnosis</th>
	<th>FinalDiagnosis</th>
	
	<th>drugs</th>
	<th>operations</th>
	<th>procedures</th>
	
	</c:if>
	<th>Edit</th>
	<th>Delete</th>
	<th>Download</th>
	</tr>
	
		<c:forEach var="patientCards" items="${patientCards}">
		<form action="/HospitalSystem/admin/updateCard?id=${patientCards.getId()}" method="post">
		<form action="/HospitalSystem/staff/deleteCard?id=${patientCards.getId()}" method="post">
		<form action="/HospitalSystem/downloadServlet?id=${patientCards.getId()}" method="post">
	
	<input type="hidden" name="id" value="${requestScope.patientCard.getId()}">
		<td>${patientCards.getDoctor().getName()}</td>
		<td>${patientCards.getDoctor().getSurname()}</td> 
		<td>${patientCards.getDoctor().getAdditionalInfo()}</td>
		
		<td>${patientCards.getPatient().getName()}</td>
		<td>${patientCards.getPatient().getSurname()}</td>
		<td>${patientCards.getPatient().getAdditionalInfo()}</td>
		
		<td>${patientCards.getNurse().getName()}</td>
		<td>${patientCards.getNurse().getSurname()}</td>
		<td>${patientCards.getNurse().getAdditionalInfo()}</td>
		
		<td>${patientCards.getStatus().toString()}</td>
	
	<c:if test="${sessionScope.role =='doctor'}">
		<td>${patientCards.getDiagnosis().toString()}</td>
		<td>${patientCards.getFinalDiagnosis().toString()}</td>
		
		<td>${patientCards.getProcedures().toString()}</td>
		<td>${patientCards.getDrugs().toString()}</td>
		
		<td>${patientCards.getOperations().toString()}</td>
	</c:if>
	
	<c:if test="${sessionScope.role =='nurse'}">
		<td>${patientCards.getProcedures().toString()}</td>
		<td>${patientCards.getDrugs().toString()}</td>
		</c:if>
	
	<c:if test="${sessionScope.role =='admin'}">
		<td> <a class="clickAbleDiv" href="/HospitalSystem/admin/updateCard?id=${patientCards.getId()}"/> Edit</a></td>
		<td> <a class="clickAbleDiv" href="/HospitalSystem/admin/deleteCard?id=${patientCards.getId()}"/> Delete</a> </td>
	</c:if>
	
		<c:if test="${sessionScope.role =='doctor'}">
		<td> <a class="clickAbleDiv" href="/HospitalSystem/staff/updateCard?id=${patientCards.getId()}"/> Edit</a> </td>
		</c:if>
	<c:if test="${patientCards.getStatus().toString() =='discharged'}">
	<td> <a class="clickAbleDiv" href="/HospitalSystem/downloadServlet?id=${patientCards.getId()}"/> Download</a> </td>
	</c:if>
		</tr>
		</form>
		</form>
		</c:forEach>
	</table>
	<c:if test="${sessionScope.role =='admin'}">
		<a href="http://localhost:9092/HospitalSystem/admin/addCard">addCard</a>
	</c:if>
	</div>
</div>
<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</body>

</html> 
