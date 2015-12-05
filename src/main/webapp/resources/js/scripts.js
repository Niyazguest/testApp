/**
 * Created by user on 12.09.15.
 */
$(document).ready(function () {
    $('#taskTable').on('dblclick', 'td[class!=tCor]', function () {
        if ($('#curInput').length > 0)
            return;
        var num = parseInt($(this).parent().attr('data-num'));
        var elem = $(this);
        $(this).replaceWith('<input id="curInput" type="text" style="z-index: 50;"/>');
        $('#curInput').css('height', elem.height().toString() + 'px');
        $('#curInput').css('width', '100%');
        $('#curInput').dblclick(function () {
            deleteInput(this, elem);
        });
    });

    $('#taskTable').on('click', '.cor', function () {
        var obj = new Object();
        var taskSaveBtn = $(this);
        obj.id = parseInt(taskSaveBtn.parent().parent().attr('data-id'));
        obj.name = taskSaveBtn.parent().parent().children('.tName').text();
        obj.definition = taskSaveBtn.parent().parent().children('.tDef').text();
        obj.taskDate = taskSaveBtn.parent().parent().children('.tDate').text();
        obj.typeId = parseInt(taskSaveBtn.parent().parent().children('.tType').children('select').val());
        obj.priorityId = parseInt(taskSaveBtn.parent().parent().children('.tPriority').children('select').val());
        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");
        var jsonData = JSON.stringify(obj);
        $.ajax({
            url: '/testapp/saveTask',
            type: 'POST',
            contentType: 'application/json;  charset=utf-8',
            dataType: 'text',
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            },
            data: jsonData,
            success: function (data, status, resp) {
                taskSaveBtn.parent().parent().attr('data-id', resp.getResponseHeader('taskId'));
                taskSaveBtn.text('Редактировать');
                alert('Задача сохранена');
            }
        })
    });

    $('#taskTable').on('click', '.del', function () {
        var taskRow = $(this);
        var taskId = $(this).parent().parent().attr('data-id');
        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");
        $.ajax({
            url: '/testapp/deleteTask',
            type: 'POST',
            dataType: 'text',
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            },
            data: {
                taskId: taskId
            },
            success: function (resp) {
                taskRow.parent().parent().remove();
                alert('Задача удалена');
            }
        })
    });
});


function addTask() {
    var number = $('.task').length + 1;
    $('#taskTable').append('<tr class="task" data-num="' + number.toString() + '">' +
        '<td style="height:100px; width:5%;" class="tNum">' + number.toString() + '</td>' +
        '<td style="height:100px; width:10%;" class="tName"></td>' +
        '<td style="height:100px; width:30%;" class="tDef"></td>' +
        '<td style="height:100px; width:10%;" class="tDate"></td>' +
        '<td style="height:100px; width:10%;" class="tType"></td>' +
        '<td style="height:100px; width:10%;" class="tPriority"></td>' +
        '<td style="height:100px; width:10%;" class="tCor"><button type=\"button\" class=\"btn btn-info btn-sm cor\">Сохранить</button>' +
        '<button type=\"button\" class=\"btn btn-info btn-sm del\">Удалить</button></td>' +
        ' </tr>');

    if (number > 1) {
        var typeElem = $('.task').eq(0).children('td.tType').html();
        $('tr[data-num=' + number.toString() + ']>td.tType').append(typeElem);
        var priorityElem = $('.task').eq(0).children('td.tPriority').html();
        $('tr[data-num=' + number.toString() + ']>td.tPriority').append(priorityElem);
    } else {
        setSelectDataFromServer('getTypes', 'type', 'tType', number.toString());
        setSelectDataFromServer('getPriorities', 'priority', 'tPriority', number.toString());
    }
}

function setSelectDataFromServer(action, selectName, tdClass, number) {
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $.ajax({
        url: '/testapp/' + action.toString(),
        type: 'GET',
        dataType: 'json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function (resp) {
            var types = document.createElement('select');
            var typeElem = $(types);
            typeElem.attr('name', selectName.toString());
            typeElem.attr('class', 'typePrioritySelect');
            var option = null;
            resp.forEach(function (item) {
                option = document.createElement('option');
                $(option).val(item.id);
                $(option).text(item.name);
                typeElem.append($(option));
            });
            $('tr[data-num=' + number + ']>td.' + tdClass.toString()).append(typeElem);
        }
    });
}

function deleteInput(input, elem) {
    var text = $(input).val();
    $(input).replaceWith(elem);
    elem.text(text);
}
