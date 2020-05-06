<%@ page pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>All ${requestScope.clientGetRole}s</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/adminListStyle.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/baseStyle.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script>
        function sort() {
            var $divs = $('form.clientForm').get().sort(function(a,b){
                var aKey = +$(a).find('p.clientsDiv').text(),
                    bKey = +$(b).find('p.clientsDiv').text();
                return aKey - bKey;
            });
            $('body').append($divs);
        }
    </script>
</head>
<body>
<h3>List of all ${requestScope.clientGetRole}s:</h3>

<c:if test="${sessionScope.role =='admin'}">
    <a href="${contextPath}/admin/sortPerson?clientGetRole=${requestScope.clientGetRole}" class="active">Sort Person</a>
</c:if>
<form class="clientForm">
    <c:forEach var="clientsList" items="${requestScope.clientsList}">
        <div id="clientDiv" class="clientsDiv">
            <p>Name: ${clientsList.name}</p>
            <p>Surname: ${clientsList.surname}</p>
            <c:choose>
                <c:when test="${requestScope.clientGetRole=='doctor'}">
                   <p>Category: ${clientsList.getAdditionalInfo()}</p>
                </c:when>
                <c:when test="${requestScope.clientGetRole=='patient'}">
                    <p>Date of birth: ${clientsList.getAdditionalInfo()}</p>
                </c:when>
            </c:choose>
        </div>
    </c:forEach>
</form>
</body>
</html>