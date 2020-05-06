
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>Hospital cards</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/adminListStyle.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/baseStyle.css">
</head>
<body>
<h3>All hospital cards:</h3>
<form class="clientForm">
    <c:forEach var="appointment" items="${requestScope.appointment}">

                <p>Doctor:</p>
                ${appointment.getDoctor().getId()}
                    Surname: ${appointment.getDoctor().getSurname()}
                <p>Patient: </p>
                ${appointment.getPatient().getId()}
                    Surname: ${appointment.getPatient().getSurname()}
    </c:forEach>
</form>
</body>
</html>
