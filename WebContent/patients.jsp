<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="dte" uri="/WEB-INF/formatDate.tld" %>
<html>

<c:set var="title" value="doctors" />

<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<br>

<div class="table-center-buttons">
		<table class = "table table-striped">
		<caption>Admin</caption>
		<tr>
		<th>id</th>
		<th>Login</th>
		<th>Password</th>
		<th>Name</th>
		<th>Surname</th>
		<th>role</th>
		<th>Birth date</th>
		<th>Delete</th>
		</tr>
		
		<c:if test="${sessionScope.role =='admin'}">
		    <a href="${contextPath}/HospitalSystem/admin/sortPatient?role=${requestScope.role}" class="active">Sort Surname by alphabet</a>
			<p><a href="${contextPath}/HospitalSystem/admin/SortCategory?role=${requestScope.role}" class="active">Sort doctors by category</a>	
		</c:if>  
		
			<c:forEach var="clientsList" items="${clientsList}">
		<tr>
			<td>${clientsList.id}</td>
			<td>${clientsList.login}</td>
			<td>${clientsList.password}</td> 
			<td>${clientsList.name}</td>
			<td>${clientsList.surname}</td>
			<td>${clientsList.role}</td>
			<td>${clientsList.additionalInfo}</td>
			<td> <a class="clickAbleDiv" href="/HospitalSystem/admin/deletePerson?id=${clientsList.getId()}"/>Delete</a> </td>
		</tr>
			</c:forEach>
		</table>
	<a href="${contextPath}/HospitalSystem/admin/AddPatient">add Patient</a>

</div>
<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</body>

</html>