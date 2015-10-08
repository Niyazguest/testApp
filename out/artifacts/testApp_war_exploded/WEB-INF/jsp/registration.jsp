<%@ page language="java" contentType="text/html; charset=utf8"
         pageEncoding="utf8" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<html>
<head>
    <title>Регистрация</title>
    <link rel="stylesheet" href="${resource_root}/css/style.css" type="text/css">
    <link rel="stylesheet" href="${resource_root}/bootstrap-3.3.5-dist/css/bootstrap.min.css" type="text/css">
    <script src="${resource_root}/bootstrap-3.3.5-dist/js/bootstrap.min.js" type="text/javascript"></script>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
</head>
<body>
<div class="container">
    <form class="form-signin" action="/testapp/registration" method="POST" accept-charset="utf-8">
        <h2 class="form-signin-heading">Зарегистрируйтесь</h2>
        <input type="text" class="form-control" placeholder="Имя" name="name" required autofocus>
        <input type="text" class="form-control" placeholder="Логин" name="login" required>
        <input type="password" class="form-control" placeholder="Пароль" name="password" required>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Регистрация</button>
    </form>
</div>

</body>
</html>
