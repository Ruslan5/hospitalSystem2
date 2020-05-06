<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html>
<c:set var="title" value="Create person" />

<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body>

<%@ include file="/WEB-INF/jspf/header.jspf" %>
<div class="table-center-buttons">
<form action="${contextPath}${action}" method="post" accept-charset="UTF-8">
    <div class="center">
    
        <input type="hidden" name="id" value="${requestScope.patientCards.getId()}">
<c:if test="${sessionScope.role =='admin'}">
        <span>Doctor login: </span>
        <select name="doctor">
            <c:forEach var="value" items="${requestScope.doctors}">
                <option value="${value.getLogin()}"
                        <c:if test="${value.getLogin() == patientCards.getDoctor().getLogin()}">selected</c:if>>${value.getLogin()}</option>
            </c:forEach>
        </select>

        <br>

        <span>Patient login: </span>
        <select name="patient">
            <c:forEach var="value" items="${requestScope.patients}">
                <option value="${value.getLogin()}"
                        <c:if test="${value.getLogin() == patientCards.getPatient().getLogin()}">selected</c:if>>${value.getLogin()}</option>
            </c:forEach>
        </select>

        <br>
        
        <span>Choose the nurse: </span>
        <select name="nurse">
            <option disabled selected value> Select nurse</option>
            <c:forEach var="value" items="${requestScope.nurses}">
                <option value="${value.getLogin()}"
                        <c:if test="${value.getLogin() == patientCards.getNurse().getLogin()}">selected</c:if>>${value.getLogin()}</option>
            </c:forEach>
        </select>

        <br>
        <span>Patient status</span>
        <select name="status">
            <c:forEach var="value" items="${requestScope.statuses}">
                <option value="${value.getName()}"
                        <c:if test="${value.getName() == patientCards.getStatus().getName()}">selected</c:if>>${value.getName()}</option>
            </c:forEach>
        </select>
</c:if>
        <br>
<c:if test="${sessionScope.role =='doctor'}">
        <span>Patient diagnosis: </span>
        <select name="diagnosis">
            <option disabled selected value>No diagnosis</option>
            <c:forEach var="value" items="${requestScope.diagnoses}">
                <option value="${value.getName()}"
                        <c:if test="${value.getName() == patientCards.getDiagnosis().getName()}">selected</c:if>>${value.getName()}</option>
            </c:forEach>
        </select>

        <br>

         <span>Patient final diagnosis: </span>
        <select name="finalDiagnosis">
            <option disabled selected value>No final diagnosis</option>
            <c:forEach var="value" items="${requestScope.diagnoses}">
                <option value="${value.getName()}"
                        <c:if test="${value.getName() == patientCards.getFinalDiagnosis().getName()}">selected</c:if>>${value.getName()}</option>
            </c:forEach>
        </select>

        <br>
        
        <span>Procedures: </span>
        <input type="text" name="procedure" placeholder="Procedure" value=<c:forEach var="procedure" items="${requestScope.patientCards.getProcedures()}">${procedure}/</c:forEach>>

        <br>

        <span>Patient drugs: </span>
        <input type="text" name="drug" placeholder="Drug" value=<c:forEach var="drug" items="${requestScope.patientCards.getDrugs()}">${drug}/</c:forEach>>

        <br>

        <span>Patient operations: </span>
        <input type="text" name="operation" placeholder="Operation" value=<c:forEach var="operation" items="${requestScope.patientCards.getOperations()}">${operation}/</c:forEach>>

        <br>
</c:if>
        <td>
      		<input type="submit" name="submit" value="update"/>
      		<input type="hidden" name="id" value="${requestScope.patientCards.getId()}">
    	</td>
        
        <input type="submit" value="Add card">
    </div>
    

 
</form>
 </div>
<%@ include file="/WEB-INF/jspf/footer.jspf"%>
	
</body>
</html>
