$(document).ready(function () {
    var queryStatus = $('#queryStatus');
    queryStatus.hide();
    var queryStatusStart = false;
    var queryResult = $('#resultTable');

    $.get('/cities')
        .done(function (data) {
            var citiesSelect = $('#city_id');
            for (var i = 0; i < data.length; i++) {
                citiesSelect.append('<option value="' + data[i].id + '">' + data[i].name + '</option>');
            }
        });

    function animateQueryStatus() {
        if (queryStatusStart) {
            queryStatus.fadeIn(800, function () {
                queryStatus.fadeOut(600, animateQueryStatus);
            });
        }
    }

    $('#submit').click(function () {
        queryResult.fadeOut(300);
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

        if (flag) {
            queryResult.html('');
            queryStatus.html('Выполнение запроса...');
            queryStatusStart = true;
            animateQueryStatus();
            $.post('ActionServlet', {
                params: JSON.stringify(params)
            })
                .done(function (data) {
                    queryStatusStart = false;
                    if (data.length != 0) {
                        queryStatus.html('');
                        queryResult.hide();
                        queryResult.html('<tr><td>ФИО</td><td>Город</td><td>Автомобиль</td></tr>');
                        for (var i = 0; i < data.length; i++) {
                            for (var k = 0; k < data[i].cars.length; k++) {
                                queryResult.append('<tr><td>' + data[i].surname + " " + data[i].name + " " + data[i].patronymic + '</td>'
                                    + '<td>' + data[i].city.name + '</td>'
                                    + '<td>' + data[i].cars[k].name + " " + data[i].cars[k].number + " " + data[i].cars[k].color + " "
                                    + data[i].cars[k].size + '</td></tr>');
                            }
                        }
                        queryResult.fadeIn(600);
                    } else {
                        queryStatus.html('Данные не найдены');
                        queryStatus.fadeIn(800);

                    }
                })
                .error(function (data) {
                    queryStatusStart = false;
                    queryStatus.html('Ошибка при выполнении запроса!');
                    queryStatus.fadeIn(800);
                })
        } else {
            queryResult.html('');
            queryStatus.html('Укажите условия поиска');
            queryStatus.fadeIn(800);
        }
    });
});
