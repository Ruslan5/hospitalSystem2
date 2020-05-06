<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@attribute name="value" required="true" type="java.lang.String" %>
<c:set var="temp" value="/${sessionScope.currentLocale}"/>
${pageContext.request.contextPath}${applicationScope.defaultLocale eq sessionScope.currentLocale ? "" : temp}${value}