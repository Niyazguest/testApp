<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ page import="ru.niyaz.test.security.UserDetailsImpl" %>
<%@ page import="org.springframework.beans.factory.annotation.Autowired" %>
<%@ page import="ru.niyaz.test.serivce.TasksService" %>
<%@ page import="java.util.List" %>
<%@ page import="ru.niyaz.test.entity.Tasks" %>
<%@ page import="ru.niyaz.test.entity.Type" %>
<%@ page import="ru.niyaz.test.entity.Priority" %>
<%@ page session="true" contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<html>
<head>
    <title>Менеджер задач</title>
    <link rel="stylesheet" href="<% request.getContextPath(); %>/resources/css/style.css" type="text/css">
    <link rel="stylesheet" href="<% request.getContextPath(); %>/resources/bootstrap-3.3.5-dist/css/bootstrap.min.css"
          type="text/css">
    <script src="<% request.getContextPath(); %>/resources/js/jquery-2.1.4.min.js" type="text/javascript"></script>
    <script src="<% request.getContextPath(); %>/resources/bootstrap-3.3.5-dist/js/bootstrap.min.js"
            type="text/javascript"></script>
    <% ServletContext servletContext = request.getSession().getServletContext(); %>
</head>
<body>
<div class="navbar navbar-inverse navbar-fixed-top" style="background-color: #1a81b4" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand"
               href="#"><% servletContext.getAttribute("userName"); %></a>
        </div>
    </div>
</div>

<div class="container">
    <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
        <div class="table-responsive">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th>№</th>
                    <th>Название задания</th>
                    <th>Определение</th>
                    <th>Дата</th>
                    <th>Приоритет</th>
                    <th>Тип</th>
                </tr>
                </thead>
                <tbody id="taskTable">
                <%
                    if (!SecurityContextHolder.getContext().getAuthentication().getName().equals("guest")) {
                        int i = 0;
                        List<Tasks> tasksList = (List<Tasks>) servletContext.getAttribute("tasks");
                        List<Type> typeList = (List<Type>) servletContext.getAttribute("types");
                        List<Priority> priorityList = (List<Priority>) servletContext.getAttribute("priorities");
                        for (Tasks task : tasksList) {
                            out.println("<tr class=\"task\" data-num=\"" + (++i) + "\">");
                            out.println("<td class=\"tNum\">" + i + "</td>");
                            out.println("<td class=\"tName\">" + task.getName() + "</td>");
                            out.println("<td class=\"tDef\">" + task.getDefinition() + "</td>");
                            out.println("<td class=\"tDate\">" + task.getTaskDate() + "</td>");
                            out.println("<td class=\"tType\"><select class=\"typePrioritySelect\" name=\"type\">");
                            for (Type type : typeList)
                                out.println("<option value=\"" + type.getTypeId().toString() + "\">" + type.getName() + "</option>");
                            out.println("</select></td>");
                            out.println("<td class=\"tPrior\"><select class=\"typePrioritySelect\" name=\"priority\">");
                            for (Priority priority : priorityList)
                                out.println("<option value=\"" + priority.getPriorityId().toString() + "\">" + priority.getName() + "</option>");
                            out.println("</select></td>");
                            out.println("<td class=\"tCor\">" + "<button type=\"button\" class=\"btn btn-info btn-sm cor\" style=\"width: 50px;\">У</button>" +
                                    "<button type=\"button\" class=\"btn btn-info btn-sm del\" style=\"width: 50px;\">У</button>" + "</td>");
                            out.println("</tr>");
                        }
                    }
                %>
                </tbody>
            </table>
            <button id="addBtn" type="button" onclick="addTask()" class="btn btn-primary">Добавить</button>
        </div>
    </div>
</div>

<script>
    $(document).ready(function () {
        $('td[class!=tCor]').click(function () {
            var num = parseInt($(this).parent().attr('data-num'));
            ;
            var elem = $(this);
            $(this).replaceWith('<input id="curInput" type="text" style="height: 100px; width: 200px; z-index: 50;"/>');
            $('#curInput').css('height', elem.height().toString() + 'px');
            $('#curInput').css('width', elem.width().toString() + 'px');
            $('#curInput').ondblclick(function () {
                deleteInput(this, elem);
            });
        });

        $('#cor').click(function () {
            var obj = new Object();
            obj.name = $(this).parent().children('.tName');
            obj.definition = $(this).parent().children('.tDef');
            obj.taskDate = $(this).parent().children('.tDate');
            var jsonData = JSON.stringify(obj);
            $.ajax({
                url: '/saveTask',
                type: 'POST',
                contentType: "application/json;  charset=utf-8",
                dataType: "text",
                data: jsonData,
                success: function () {
                    alert('Задача сохранена');
                }
            })
        });
    });

    function addTask() {
        var number = $('.task').length + 1;
        $('#taskTable').appendChild('<tr class="task" data-num="' + number.toString() + '">' +
                '<td class="tNum">' + number.toString() + '</td>' +
                '<td class="tName"></td>' +
                '<td class="tDef"></td>' +
                '<td class="tDate"></td>' +
                '<td class="tType"></td>' +
                '<td class="tPrior"></td>' +
                '<td class="tCor"><button type=\"button\" class=\"btn btn-info btn-sm cor\" style=\"width: 50px;\">Ред</button>' +
                '<button type=\"button\" class=\"btn btn-info btn-sm del\" style=\"width: 50px;\">У</button></td>' +
                ' </tr>');
    }

    function deleteInput(elem) {
        var text = $(this).val();
        $(this).replaceWith(elem);
        elem.text(text);
    }
</script>
</body>
</html>
