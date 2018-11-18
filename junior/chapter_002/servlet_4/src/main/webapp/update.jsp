<%--
  Created by IntelliJ IDEA.
  User: vichu
  Date: 14.11.2018
  Time: 23:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset='UTF-8'>
    <title>Update User</title>
</head>
<body>
<form method='post'>
    <input type='hidden' name='action' value='update'>
    <input type='hidden' name='id' value=${id}>
    Name:
    <input type='text' name='name' value=${name}><br>
    Login:
    <input type='text' name='login' value=${login}><br>
    Email:
    <input type='text' name='email' value=${email}><br>
    <br>
    <br>
    <input type='submit' value='UPDATE'>
</form>

</body>
</html>
