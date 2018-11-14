<%@ page import="ru.job4j.model.User" %>
<%@ page import="ru.job4j.logic.ValidateService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset='UTF-8'>
    <title>User Service v3.0</title>
    <style>
        .inp {
            position: relative;
            top: 8px;
        }
    </style>
</head>
<body>
<table border="1">
    <tr>
        <td align='center'>Name</td>
        <td align='center'>Login</td>
        <td align='center'>Email</td>
    </tr>
    <% for (User u : ValidateService.getInstance().findAll()) { %>
    <tr>
        <td>
            <%=u.getName()%>
        </td>
        <td>
            <%=u.getLogin()%>
        </td>
        <td>
            <%=u.getEmail()%>
        </td>
        <td>
            <form method='get' action='/update'>
                <input type='hidden' name='id' value=<%=u.getId()%>>
                <input class="inp" align="bottom" type='submit' value='UPDATE'>
            </form>
        </td>
    </tr>
    <% } %>
</table>
<br>
<br>
<form method='get' action='/create'>
    <input type='submit' value='CREATE'>
</form>
</body>
</html>
