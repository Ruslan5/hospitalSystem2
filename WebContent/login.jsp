<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<%@ page import="ua.nure.mirzoiev.hospitalSystem.entity.Role" %>
<%@ taglib uri="WEB-INF/currentDate.tld" prefix="m" %>  
<html>

<c:set var="title" value="Login" />
<%@ include file="/WEB-INF/jspf/head.jspf" %>
	
<body>


<%@ include file="/WEB-INF/jspf/header.jspf"%>


<div class="form text-center">	

<!-- Собственный тег Custom tad library -->
<div class="date-time">Current Date and Time is: <m:today/>  </div>

	<c:if test="${empty role}">
	  
		<form class="form-signin" action="login" method="post">
			<h1 class="h3 mb-3 font-weight-normal">Please sign in</h1>
			<fm:message key="login.login" var="login" />
		    <input type="text" id="inputEmail" class="form-control" placeholder="Enter Username" name="login" required autofocus> <br>
		
		    <input type="password"  id="inputPassword" class="form-control" placeholder="Enter Password" name="password" required> 
			
			<fm:message key="login.submit" var="submit" />
	  		<button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
			
		</form>
	</c:if>


<c:if test="${not empty role}">

<c:if test="${sessionScope.role =='admin'||sessionScope.role =='nurse'||sessionScope.role =='doctor'}">
    		
<div class="main_imgs">
     <h1 class="text-center">Hospital Management System </h1>
 <div class="container">
  <div class="row">
  
   <div class="col-lg-4 col-md-4 col-sm-12">
    <img src="${pageContext.request.contextPath}/img/doctors.png" class="img-fluid">
    <a href="/HospitalSystem/admin/doctors">Doctors</a>
   </div>
  
   <div class="col-lg-4 col-md-4 col-sm-12">
    <img src="${pageContext.request.contextPath}/img/patient.png" class="img-fluid">
    <a href="/HospitalSystem/admin/patients">Patients</a>
    </div>
   
   <div class="col-lg-4 col-md-4 col-sm-12">
    <img src="${pageContext.request.contextPath}/img/card.png" class="img-fluid">
    <a href="/HospitalSystem/admin/patientCards">Cards</a>
   </div>
   
  </div>
 </div>
</div>
	</c:if>
</c:if>

</div>

<%@ include file="/WEB-INF/jspf/footer.jspf"%>
		
</body>
</html>