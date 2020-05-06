<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="dte" uri="/WEB-INF/formatDate.tld" %>
<!DOCTYPE html>
<html>

<c:set var="title" value="Create person" />

<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body>

<%@ include file="/WEB-INF/jspf/header.jspf" %>
<div class="table-center-buttons">
	<h3>New Person</h3>
	<form method="post">
		<label>login</label><br> <input required name="login" /><br> <label>Password</label><br>
		<input required name="password" /><br> <label>name</label><br> <input required
			name="name" /><br> <label>surname</label><br> <input
			name="surname" /><br> <span>Role</span> <select id="role"
			name="role">
			<c:forEach var="value" items="${requestScope.roles}">
				<option value="${value.name}"
					<c:if
                        test="${value.name eq requestScope.client.getRole().getName()}"> selected</c:if>>${value.name}</option>
			</c:forEach>
		</select> <br> <label>Doctor category: pediatrician,
			traumatologist, surgeon...
		</label><br> <input required name="additionalInfo" /><br> <input
			type="submit" value="Add Person" />
	</form>
</div>

	<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</body>
</html>