<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Менеджер задач</title>
    <link rel="stylesheet" href="${resource_root}/css/style.css" type="text/css">
    <link rel="stylesheet" href="${resource_root}/bootstrap-3.3.5-dist/css/bootstrap.min.css" type="text/css">
    <script src="${resource_root}/bootstrap-3.3.5-dist/js/bootstrap.min.js" type="text/javascript"></script>
</head>
<body>
<script type="text/javascript" charset="utf-8">
   function registrate() {
       window.location="/testapp/toRegistration";
   }

</script>


<div class="container">
    <form class="form-signin" action="/testapp/login" method="POST">
        <h2 class="form-signin-heading">Вход</h2>
        <input type="text" class="form-control" placeholder="Логин" name="j_username" required autofocus>
        <input type="password" class="form-control" placeholder="Пароль" name="j_password" required>
        <label class="checkbox">
            <input type="checkbox" value="remember-me" name="_spring_security_remember_me"> Запомнить меня
        </label>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Вход</button>
    </form>
    <button class="btn btn-lg btn-info reg" onclick="registrate()">Регистрация</button>
</div>

</body>
</html>
