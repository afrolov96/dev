<%@ page language="java" contentType="text/html; ISO-8859-1" pageEncoding="UTF-8" %>
<%@ page session="true" %>

<script src="lib/jquery.min.js"></script>
<script>
    $(document).ready(function () {
        $.get('/cities')
                .done(function (data) {
                    var citiesSelect = $('#city_id');
                    for (var i = 0; i < data.length; i++) {
                        citiesSelect.append('<option value="' + data[i].id + '">' + data[i].name + '</option>');
                    }
                });

        $('#submit').click(function () {
            var flag = false;
            var params = {};
            $('input[type=text]').each(function () {
                var key = $(this).attr('id');
                var val = $(this).val();
                if (val != '') {
                    params[key] = val;
                    flag = true;
                }
            });

            var key = 'cities_id';
            var val = $("#city_id :selected").val();
            if (val.length > 0) {
                params[key] = val;
                flag = true;
            }

            var queryStatus = $('#queryStatus');
            var queryResult = $('#resultTable');
            if (flag) {
                $.post('ActionServlet', {
                    params: JSON.stringify(params)
                })
                        .done(function (data) {
                            if (data.length != 0) {
                                queryStatus.html('');
                                queryResult.html('<tr><td>ФИО</td><td>Город</td><td>Автомобиль</td></tr>');
                                for (var i = 0; i < data.length; i++) {
                                    for (var k = 0; k < data[i].cars.length; k++) {
                                        queryResult.append('<tr><td>' + data[i].surname + " " + data[i].name + " " + data[i].patronymic + '</td>'
                                                + '<td>' + data[i].city.name + '</td>'
                                                + '<td>' + data[i].cars[k].name + " " + data[i].cars[k].number + " " + data[i].cars[k].color + " "
                                                + data[i].cars[k].size + '</td></tr>');
                                    }
                                }
                            } else {
                                queryResult.html('');
                                queryStatus.html('Данные не найдены');
                            }
                        })
            } else {
                queryResult.html('');
                queryStatus.html('Укажите условия поиска');
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
    <tr>
        <td>
            <select id="city_id">
                <option value=""></option>
            </select>
        </td>
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

<div id="queryStatus"></div>
<br>
<table id="resultTable">
</table>
</body>
</html>
