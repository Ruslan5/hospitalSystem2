<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<%@ page import="ua.nure.mirzoiev.hospitalSystem.entity.Role" %>

<html>
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<c:set var="title" value="admin page" />

<body>
<%@ include file="/WEB-INF/jspf/header.jspf"%>
<div class="table-center-buttons">
			<!-- Language switching -->
			<div class = "language">
				<form action="language" method="post">
					<input type="hidden" name="command" value="language">
					<input type="hidden" name="language" value="en">
					<input type="hidden" name=url value="${requestScope['javax.servlet.forward.servlet_path']}">
					<input type="image" src="img/usa.png" alt="Submit">
				</form>
	
				<form action="language" method="post">
					<input type="hidden" name="command" value="language">
					<input type="hidden" name="language" value="uk">
					<input type="hidden" name=url value="${requestScope['javax.servlet.forward.servlet_path']}">
					<input type="image" src="img/ua.png" alt="Submit">
				</form>
			</div>
	<table id="main-container">
	
		<tr>
	<td class="content">
	
		<form action="${contextPath}/admin/doctors">	
		<button type="submit"><fmt:message key="admin.doctors"/></button>
		</form>
	
		<form action="${contextPath}/admin/patients">
		<button type="submit"><fmt:message key="admin.patients"/></button>
		</form>
	
		<form action="${contextPath}/admin/patientCards">
		<fmt:message key="login.submit" var="submit" />
		<button type="submit"><fmt:message key="admin.patientCard"/></button>
		</form>
	</td>
	</tr>
	</table>

</div>
<%@ include file="/WEB-INF/jspf/footer.jspf"%>

</body>
</html>
