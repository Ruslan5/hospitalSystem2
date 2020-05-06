<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html>


<c:set var="title" value="Add new Patient" />

<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body>

<%@ include file="/WEB-INF/jspf/header.jspf" %>
<div class="center">
<div class="table-center-buttons">
    <form action="${contextPath}${action}" method="post">

        <input type="hidden" name="id" value="${requestScope.person.getId()}">
        
        <span>Login</span>
        <input type="text" name="login" value="${requestScope.person.getLogin()}" placeholder="Login" required>

        <br>

        <span>Password</span>
        <input type="text" name="password" value="${requestScope.person.getPassword()}" placeholder="Password" required>

        <br>

        <span>Name</span>
        <input type="text" name="name" value="${requestScope.person.getName()}" placeholder="Name" required>

        <br>

        <span>Surname</span>
        <input type="text" name="surname" value="${requestScope.person.getSurname()}" placeholder="Surname" required>

        <br>

        <div id="PatDate" style="display: none">
            <span>Patient date of birth</span>
            <input type="text" name="additionalInfo" value="${requestScope.person.getAdditionalInfo()}" placeholder="DD-MM-YYYY">
        </div>     

        <br>
        <div id="docCat" style="display: none">
            <span>Category</span>
            <input type="text" name="additionalInfo">
        </div>
        
        <span>Role</span>
        <select id="role" name="role">
            <c:forEach var="value" items="${requestScope.roles}">
                <option value="${value.name}" <c:if
                        test="${value.name eq requestScope.person.getRole().getName()}"> selected</c:if>>${value.name}</option>
            </c:forEach>
        </select>
        <br>

        <input type="submit" value="Add patient">
    </form>
</div>
</div>
<%@ include file="/WEB-INF/jspf/footer.jspf"%>

</body>
<script>
    $(document).ready(function () {
        $('#role').on('change', function () {
            if (this.value === "doctor") {
                $("#docCat").show();
                $("#PatDate").hide();
            } else if (this.value === "patient") {
                $("#PatDate").show();
                $("#docCat").hide();
            } else {
                $("#docCat").hide();
                $("#PatDate").hide();
            }
        })
    })
</script>
</html>
