<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>

<html>
<head>
    <title>Title</title>
</head>
<body>
    <c:if test="${sessionScope.user != null}">
        <form action="/logout" method="get">
            <input type="submit" value="Logout">
        </form
    </c:if>
</body>
</html>
