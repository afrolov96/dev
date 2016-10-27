<%@ page language="java" contentType="text/html; ISO-8859-1" pageEncoding="UTF-8" %>
<%@ page session="true" %>

<script src="lib/jquery.min.js"></script>
<script>
    $(document).ready(function () {
        $.get('ActionServlet')
                .done(function (data) {
                    $('#cities_name').html(data);
                })

        $('#submit').click(function () {
            var params = [];
            $('input[type=text]').each(function () {
                var key = $(this).attr('id');
                var val = $(this).val();
                if (val != '') {
                    item = {};
                    item [key] = val;
                    params.push(item);
                }
            });
            var val = $("#cities_name :selected").val();
            if (val != 'xxx') {
                var key = 'cities_name';
                item = {};
                item [key] = val;
                params.push(item);
            }
            if (params.length != 0) {
                $.post('ActionServlet', {
                    params: JSON.stringify(params)
                })
                        .done(function (data) {
                            $('#searchResult').html(data)
                        })
            } else {
                $('#searchResult').html("</br>Укажите условия поиска");
            }
        });
    });
</script>
<html>
<%
    if (request.getParameter("remember") != null) {
        String remember = request.getParameter("remember");
        Cookie cookieUserName = new Cookie("cookuser", request.getParameter("username").trim());
        Cookie cookiePassword = new Cookie("cookpassword", request.getParameter("password").trim());
        Cookie cookieRemember = new Cookie("cookremember", remember.trim());
        cookieUserName.setMaxAge(60 * 60 * 24 * 10);
        cookiePassword.setMaxAge(60 * 60 * 24 * 10);
        cookieRemember.setMaxAge(60 * 60 * 24 * 10);
        response.addCookie(cookieUserName);
        response.addCookie(cookiePassword);
        response.addCookie(cookieRemember);
    }
    HttpSession httpSession = request.getSession();
%>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
Пользователь:&nbsp <%= httpSession.getAttribute("sessionUser")%>
<form action="Logout" method="post">
    <input type="submit" value="Выйти">
</form>

<br>

<h1>Поиск людей</h1>
</head>
<body>
<table>
    <tr>
        <td>Фамилия</td>
        <td>Имя</td>
        <td>Отчество</td>
    </tr>
    <tr>
        <td><input type="text" id="persons_surname"/></td>
        <td><input type="text" id="persons_name"/></td>
        <td><input type="text" id="persons_patronymic"/></td>
    </tr>
    <tr>
        <td>Город</td>
    </tr>
    <!--td><input type="text" id="cities_name"/></td></tr-->
    <tr>
        <td id="cities_name"></td>
    </tr>
    </tr>
    <tr>
        <td>Автомобиль</td>
        <td>Номер</td>
        <td>Цвет</td>
        <td>Класс</td>
    </tr>
    <tr>
        <td><input type="text" id="cars_name"/></td>
        <td><input type="text" id="cars_num"/></td>
        <td><input type="text" id="cars_color"/></td>
        <td><input type="text" id="cars_size"/></td>
    </tr>
</table>
<input type="button" id="submit" value="Найти"/>
<br>

<div id="searchResult"></div>
</body>
</html>
