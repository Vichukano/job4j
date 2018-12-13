<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %><html>
<html>
<head>
    <meta charset='UTF-8'>
    <title>Update User</title>
</head>
<body>
<form method='post'>
    <input type='hidden' name='action' value='update'>
    <input type='hidden' name='id' value=<c:out value="${id}"></c:out>>
    Login:
    <input type='text' name='login' value=<c:out value="${login}"></c:out>><br>
    Password:
    <input type='text' name='password' value=<c:out value="${password}"></c:out>><br>
    Email:
    <input type='text' name='email' value=<c:out value="${email}"></c:out>><br>
    <table>
        <tr>
            <td>Role:</td>
            <td><c:out value="${roleName}"></c:out></td>
        </tr>
    </table>
    <input  type="hidden" name="roleName" value="<c:out value="${roleName}"></c:out>">
    <c:if test="${role == 'Admin'}">
    <br>
    <select name="role">
        <option value="Admin">Admin</option>
        <option value="User">User</option>
    </select>
    </c:if>
    <br>
    <br>
    <input type='submit' value='UPDATE'>
</form>
</body>
</html>
