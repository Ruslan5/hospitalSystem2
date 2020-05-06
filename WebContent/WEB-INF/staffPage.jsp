<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
c:set var="title" value="Cards" />

<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body>
<h3>List of all your patients:</h3>

<form class="clientForm">
    <c:forEach var="client" items="${requestScope.clientsList}">
        <div class="clientsDiv">
            <p>Name: ${client.getName()}</p>
            <p>Surname: ${client.getSurname()}</p>
            <p>Date of birth: ${client.getAdditionalInfo()}</p>
        </div>
    </c:forEach>
</form>
<a href="http://localhost:9092/HospitalTest2/staff/patientCards">Cards</a>
</body>
</html>
