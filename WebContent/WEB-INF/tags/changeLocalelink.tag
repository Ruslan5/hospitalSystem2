<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@attribute name="locale" required="true" type="java.lang.String" %>
${pageContext.request.contextPath}/${locale}${requestScope.partialUrl}