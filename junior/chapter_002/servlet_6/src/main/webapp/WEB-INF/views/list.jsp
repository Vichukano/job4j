<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset='UTF-8'>
    <title>User Service v6.0</title>
</head>
<body>
<table border="1">
    <tr>
        <td align='center'>Login</td>
        <td align='center'>Email</td>
        <td align='center'>Role</td>
        <td align='center'>Password</td>
    </tr>
    <tr>
        <c:forEach items="${users}" var="user">
        <td>
            <c:out value="${user.login}"></c:out>
        </td>
        <td>
            <c:out value="${user.email}"></c:out>
        </td>
        <td>
            <c:out value="${user.roleName}"></c:out>
        </td>
        <c:if test="${role == 'Admin'}">
            <td>
                <c:out value="${user.password}"></c:out>
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
        </c:if>
        <c:if test="${role != 'Admin'}">
            <c:if test="${id == user.id}">
                <td>
                    <c:out value="${user.password}"></c:out>
                </td>
                <td>
                    <form method='get' action='/update'>
                        <input type='hidden' name='id' value=<c:out value="${user.id}"></c:out>>
                        <input align="bottom" type='submit' value='UPDATE'>
                    </form>
                </td>
            </c:if>
        </c:if>
    </tr>
    </c:forEach>
</table>
<br>
<br>
<c:if test="${role == 'Admin'}">
    <table>
        <tr>
            <td>
                <form method='get' action='/create'>
                    <input type='submit' value='CREATE'>
                </form>
            </td>
            </c:if>
            <td>
                <form method='get' action='/signin'>
                    <input type='submit' value='LOGOUT'>
                </form>
            </td>
        </tr>
    </table>
</body>
</html>
