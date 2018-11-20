<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %><html>
<head>
    <meta charset='UTF-8'>
    <title>User Service v5.0</title>
</head>
<body>
<table border="1">
    <tr>
        <td align='center'>Name</td>
        <td align='center'>Login</td>
        <td align='center'>Email</td>
    </tr>
    <c:forEach items="${users}" var="user">
    <tr>
        <td>
            <c:out value="${user.name}"></c:out>
        </td>
        <td>
            <c:out value="${user.login}"></c:out>
        </td>
        <td>
            <c:out value="${user.email}"></c:out>
        </td>
        <td>
            <form method='get' action='/update'>
                <input type='hidden' name='id' value=<c:out value="${user.id}"></c:out>>
                <input align="bottom" type='submit' value='UPDATE'>
            </form>
            <form method='get' action='/delete'>
                <input type='hidden' name='id' value=<c:out value="${user.id}"></c:out>>
                <input type='hidden' name='action' value='delete'>
                <input align="bottom" type='submit' value='DELETE'>
            </form>
        </td>
    </tr>
    </c:forEach>
</table>
<br>
<br>
<form method='get' action='/create'>
    <input type='submit' value='CREATE'>
</form>
</body>
</html>
