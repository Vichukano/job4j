<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset='UTF-8'>
    <title>Create User</title>
</head>
<body>
<form method='post' action="/create">
    <input type='hidden' name='action' value='add'>
    <input type='hidden' name='id' value=''>
    Name:
    <input type='text' name='name' value=''><br>
    Login:
    <input type='text' name='login' value=''><br>
    Email:
    <input type='text' name='email' value=''><br>
    <br>
    <br>
    <input type='submit' value='SAVE'>
</form>
</body>
</html>
