<%@ page import="java.util.List" %>
<%@ page import="ru.niyaz.test.entity.Tasks" %>
<%@ page import="ru.niyaz.test.entity.Priority" %>
<%@ page import="ru.niyaz.test.entity.Type" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true" contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<html>
<head>
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    <title>Менеджер задач</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/resources/css/style.css" type="text/css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/resources/bootstrap-3.3.5-dist/css/bootstrap.min.css"
          type="text/css">
    <script src="<%= request.getContextPath() %>/resources/js/jquery-2.1.4.min.js" type="text/javascript"></script>
    <script src="<%= request.getContextPath() %>/resources/bootstrap-3.3.5-dist/js/bootstrap.min.js"
            type="text/javascript"></script>
    <script src="<%= request.getContextPath() %>/resources/js/scripts.js" type="text/javascript"></script>
    <% ServletContext servletContext = request.getSession().getServletContext(); %>
</head>
<body>
<div class="navbar navbar-inverse navbar-fixed-top" style="background-color: #1a81b4" role="navigation">
    <div class="container">
        <div class="navbar-header" style="width: 100%;">
            <div class="navbar-brand" style="color: white;"><%= servletContext.getAttribute("userName")%>
            </div>
            <c:url var="logoutUrl" value="/logout"/>
            <a class="navbar-brand" href="#" onclick="$('#logout').submit()" style="color: #d4d4d4; position: relative; left: 90%;">Выйти</a>
            <form action="${logoutUrl}" id="logout" method="post" style="height: 5%; width: 5%">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </form>
        </div>
    </div>
</div>

<div class="container">
    <div style="width: 105%; margin-left: -2.5%;">
        <div class="table-responsive">
            <table class="table table-striped table-bordered tableBody">
                <thead>
                <tr>
                    <td>№</td>
                    <td>Название задания</td>
                    <td>Определение</td>
                    <td>Дата</td>
                    <td>Приоритет</td>
                    <td>Тип</td>
                    <td>Операции</td>
                </tr>
                </thead>
                <tbody id="taskTable">
                <%
                    int i = 0;
                    Long typeId;
                    Long priorityId;
                    List<Priority> priorities = (List<Priority>) servletContext.getAttribute("priorities");
                    List<Type> types = (List<Type>) servletContext.getAttribute("types");
                    List<Tasks> tasksList = (List<Tasks>) servletContext.getAttribute("tasks");
                    for (Tasks task : tasksList) {
                        out.println("<tr class=\"task\" data-id=\"" + task.getTaskId() + "\" data-num=\"" + (++i) + "\">");
                        out.println("<td style=\"height:100px; width:10%;\" class=\"tNum\">" + i + "</td>");
                        out.println("<td style=\"height:100px; width:30%;\" class=\"tName\">" + task.getName() + "</td>");
                        out.println("<td style=\"height:100px; width:10%;\" class=\"tDef\">" + task.getDefinition() + "</td>");
                        out.println("<td style=\"height:100px; width:10%;\" class=\"tDate\">" + task.getTaskDate() + "</td>");
                        out.println("<td style=\"height:100px; width:10%;\" class=\"tType\"><select name=\"type\">");
                        typeId = task.getType().getTypeId();
                        priorityId = task.getPriority().getPriorityId();
                        for (Type type : types) {
                            out.println("<option value=\"" + type.getTypeId() + "\"" + ((type.getTypeId() == typeId) ? " selected" : "") + ">");
                            out.print(type.getName());
                            out.println("</option>");
                        }
                        out.println("</select></td>");
                        out.println("<td style=\"height:100px; width:10%;\" class=\"tPriority\"><select name=\"priority\">");
                        for (Priority priority : priorities) {
                            out.println("<option value=\"" + priority.getPriorityId() + "\">");
                            out.print(priority.getName());
                            out.println("</option>");
                        }
                        out.println("</select></td>");
                        out.println("<td style=\"height:100px; width:10%;\" class=\"tCor\">" + "<button type=\"button\" class=\"btn btn-info btn-sm cor\">Редактировать</button>" +
                                "<button type=\"button\" class=\"btn btn-info btn-sm del\">Удалить</button>" + "</td>");
                        out.println("</tr>");
                    }

                %>
                </tbody>
            </table>
            <button id="addBtn" type="button" onclick="addTask()" class="btn btn-primary">Добавить</button>
        </div>
    </div>
</div>

</body>
</html>
