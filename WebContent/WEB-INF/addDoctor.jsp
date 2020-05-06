<!DOCTYPE html>
<html>

<c:set var="title" value="Doctor" />

<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body>


<%@ include file="/WEB-INF/jspf/header.jspf" %>
<div class="table-center-buttons">
<div class="form text-center">	
	
	<h3>New doctor</h3>
	<form method="post">
	<label>login</label><br>
	<input name="login"/><br>
	<label>Password</label><br>
	<input name="password"/><br>
	<label>name</label><br>
	<input name="name"/><br>
	<label>surname</label><br>
	<input name="surname"/><br>
	<label>Role</label><br>
	
	<select size="1" multiple name="role">
	    <option selected value="doctor">doctor</option>
	   </select>
	<br>
	<label>AdditionalInfo</label><br>
	<input name="additionalInfo"/><br>
	<br>
	<label>Category</label><br>
	<p><select size="3" multiple name="category">
	    <option value="pediatrician">pediatrician</option>
	    <option selected value="traumatologist">traumatologist</option>
	    <option value="surgeon">surgeon</option>
	   </select></p>
	<br>
	<input type="submit" value="Save" />
	</form>
</div>
</div>
<%@ include file="/WEB-INF/jspf/footer.jspf"%>
	
</body>
</html>