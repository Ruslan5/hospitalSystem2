<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<%
	String login= request.getParameter("login");
	String password = request.getParameter("password");
	
	if((login.equals("admin")&&password.equals("admin"))){
		response.sendRedirect("admin.jsp");
	}else{
		response.sendRedirect("error.jsp");
	}

%>

</body>
</html>