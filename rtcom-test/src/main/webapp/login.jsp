<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page session="true" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>

<%
    Cookie[] cookies = request.getCookies();
    String userName = "";
    String password = "";
    String remember = "";
    if (cookies != null) {
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("cookuser")) {
                userName = cookie.getValue();
            }
            if (cookie.getName().equals("cookpassword")) {
                password = cookie.getValue();
            }
            if (cookie.getName().equals("cookremember")) {
                remember = cookie.getValue();
            }
        }
    }
%>

<form method="POST" action="/">
    <table>
        <tr>
            <td colspan="2">Введите имя пользователя и пароль:</td>
        </tr>
        <tr>
            <td>Имя:</td>
            <td><input type="text" name="username" autocomplete="off" value="<%=userName%>"/></td>
        </tr>
        <tr>
            <td>Пароль:</td>
            <td><input type="password" name="password" autocomplete="off" value="<%=password%>"/></td>
        </tr>
        <tr>
            <td colspan="2"><input type="submit" value="Войти"/></td>
        </tr>
        <tr>
            <td>Запомнить меня</td>
            <td><input type="checkbox" name="remember"
                       value="1" <%= "1".equals(remember.trim()) ? "checked='checked'" : ""%>/></td>
        </tr>
    </table>
</form>
<div id="msg"><%= session.getAttribute("msg") != null ? session.getAttribute("msg").toString() : ""%>
</div>

</body>
</html>