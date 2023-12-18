
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page isELIgnored="false" %>

<html>
<head>
    <title>Title</title>
</head>
<body>
    <form action="/login" method="post">
        <label for="user_login">
            <span>Login:</span>
            <input name="user_login" type="text" id="user_login">
        </label><br>
        <label for="user_password">
            <span>Password:</span>
            <input name="user_password" type="password" id="user_password">
        </label><br>
        <input type="submit" value="Submit">
    </form>
    <c:forEach var="error" items="${requestScope.errors}">
        ${error.getMessage()}<br>
    </c:forEach>
</body>
</html>
