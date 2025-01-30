<%@ page import="org.plenkovii.dto.FinishedMatchDto" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    List<FinishedMatchDto> matches = (List<FinishedMatchDto>) request.getAttribute("matches");
    int currentPage = (int) request.getAttribute("page");
    int totalPages = (int) request.getAttribute("totalPages");
    String name = (String) request.getAttribute("name");
%>
<html>
<head>
    <title>Завершенные матчи</title>
</head>
<body>
<jsp:include page="header.jsp"/>
<div class="container">
    <form class="search-section" action="matches" method="get">
        <label>
            Поиск игрока по имени
            <input type="text" name="filter_by_player_name">
            <button type="submit">Искать!</button>
        </label>
    </form>
    <table class="matches-table">
        <thead>
        <tr>
            <th>Игрок 1</th>
            <th>Игрок 2</th>
            <th>Победитель</th>
        </tr>
        </thead>
        <%
            if (matches != null) {
                for (FinishedMatchDto match : matches) {

        %>
        <tr>
            <td><%=match.getPlayer1Name()%>
            </td>
            <td><%=match.getPlayer2Name()%>
            </td>
            <td class="winner-name"><%=match.getWinnerName()%>
            </td>
        </tr>
        <%
                }
            }
        %>
    </table>

    <div class="pagination">
        <form action="matches" method="get">
            <input type="hidden" name="filter_by_player_name" value="<%= (name == null) ? "" : name%>">

            <% if (currentPage > 1) { %>
            <button type="submit" name="page" value="<%= currentPage - 1 %>">Предыдущая</button>
            <% } %>

            <% for (int i = 1; i <= totalPages; i++) { %>

            <button <% if (i == currentPage) {%> class="current-page" <%}%> type="submit" name="page"
                                                 value="<%= i%>"><%= i%>
            </button>
            <% } %>

            <% if (currentPage < totalPages) { %>
            <button type="submit" name="page" value="<%= currentPage + 1 %>">Следующая</button>
            <% } %>
        </form>
    </div>

    <%--    <div class="pagination">--%>
    <%--        <% if (currentPage > 1) { %>--%>
    <%--        <a href="?page=<%= currentPage - 1 %>">Предыдущая</a>--%>
    <%--        <% } %>--%>

    <%--        <% for (int i = 1; i <= totalPages; i++) { %>--%>
    <%--        <a href="?page=<%= i %>"--%>
    <%--                <%= (i == currentPage) ? "style='font-weight:bold;'" : "" %>>--%>
    <%--            <%= i %>--%>
    <%--        </a>--%>
    <%--        <% } %>--%>


    <%--        <% if (currentPage < totalPages) { %>--%>
    <%--        <a href="?page=<%= currentPage + 1 %>">Следующая</a>--%>
    <%--        <% } %>--%>
    <%--    </div>--%>
</div>
</body>
</html>
