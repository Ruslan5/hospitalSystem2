<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
c:set var="title" value="Patients" />

<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<div class="table-center-buttons">

<table class="table table-striped">
<caption>Admin</caption>
<tr>
<th>id</th>
<th>Login</th>
<!--  <th>Password</th>  -->
<th>Name</th>
<th>Surname</th>
<th>role</th>
<th>additionalInfo</th>

</tr>

	<c:forEach var="items" items="${list}">
<tr>
	<td>${items.id}</td>
	<td>${items.login}</td>
	<!-- <td>${items.password}</td>  -->
	<td>${items.name}</td>
	<td>${items.surname}</td>
 	<td>${items.role}</td>
	<td>${items.additionalInfo}</td>

</tr>
	</c:forEach>
</table>

<a href="http://localhost:9092/HospitalTest2/AddPerson">add Person</a>

</div>

<%@ include file="/WEB-INF/jspf/footer.jspf"%>

</body>

</html>