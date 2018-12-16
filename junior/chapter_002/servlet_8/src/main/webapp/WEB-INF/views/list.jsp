<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset='UTF-8'>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <!-- Optional theme -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
          integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
    <!--JQuery-->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <!-- Latest compiled and minified JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
            integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
            crossorigin="anonymous"></script>
    <!--javascript functions-->
    <title>User Service v8.0</title>
</head>
<body>
<div class="container-fluid">
    <br>
    <div class="col-md-9"></div>
    <div class="col-md-9">
        <table class="table table-striped" id="table1">
            <thead class="text-left" style="background-color: lightcyan">
            <tr>
                <th>Login</th>
                <th>Email</th>
                <th>Role</th>
                <th>Country</th>
                <th>City</th>
                <th>Password</th>
                <th>Action</th>
            </tr>
            </thead>
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
                <td>
                    <c:out value="${user.country}"></c:out>
                </td>
                <td>
                    <c:out value="${user.city}"></c:out>
                </td>
                <c:if test="${role == 'Admin'}">
                    <td>
                        <c:out value="${user.password}"></c:out>
                    </td>
                    <td>
                        <form id="myForm" method='get' action='/update'>
                            <div class="form-group" id="id-group">
                                <input type='hidden' name='id' value=<c:out value="${user.id}"></c:out>>
                            </div>
                            <div class="form-group text-left">
                                <label class="control-label">Update</label><br>
                                <button class="btn btn-success" type="submit">
                                    <i class="glyphicon glyphicon-pencil"></i>
                                </button>
                            </div>
                        </form>
                        <form method='get' action='/delete'>
                            <div class="form-group" id="secondId">
                                <input type='hidden' name='id' value=<c:out value="${user.id}"></c:out>>
                            </div>
                            <input type='hidden' name='action' value='delete'>
                            <div class="form-group text-left">
                                <label class="control-label">Delete</label><br>
                                <button class="btn btn-danger" type="submit">
                                    <i class="glyphicon glyphicon-remove"></i>
                                </button>
                                <!--<input align="bottom" type='submit' id="del" value='DELETE'>-->
                            </div>
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
                                <div class="form-group">
                                    <input type='hidden' name='id' value=<c:out value="${user.id}"></c:out>>
                                    <label class="control-label">Update</label><br>
                                    <button class="btn btn-success" type="submit">
                                        <i class="glyphicon glyphicon-pencil"></i>
                                    </button>
                                </div>
                            </form>
                        </td>
                    </c:if>
                </c:if>
            </tr>
            </c:forEach>
        </table>
    </div>
    <br>
    <br>
    <div class="container-fluid">
        <div class="col-md-6">
            <c:if test="${role == 'Admin'}">
            <table>
                <tr>
                    <td>
                        <form method='get' action='/create'>
                            <div class="form-group">
                                <button class="btn btn-info" type="submit">
                                    Create user
                                </button>
                            </div>
                        </form>
                    </td>
                    </c:if>
                    <td>
                        <form method='get' action='/signin'>
                            <div class="form-group">
                                <button class="btn btn-info" style="margin-left: 10px" type="submit">
                                    Logout
                                </button>
                            </div>
                        </form>
                    </td>
                </tr>
            </table>
        </div>
    </div>
</div><br><br><br>
<div class="footer navbar-fixed-bottom text-right" style="background-color: lightcyan">
    <div class="col-md-11 text-right">
        <i class="glyphicon glyphicon-envelope"></i>
        <label>vichukano@gmail.com</label><br>
        <i class="glyphicon glyphicon-user"></i>
        <a href="https://vk.com/vichukano" target="_blank">ВКонтакте</a>
    </div>
</div>
</body>
</html>
