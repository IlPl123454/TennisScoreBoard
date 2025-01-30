
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error</title>
</head>
<body>
<jsp:include page="header.jsp"/>
<div class="exceptions">
    <h3>Ошибка: ${errorMessage}</h3>
    <form action="new-match.jsp" method="get">
        <button type="submit">Вернуться к созданию нового матча</button>
    </form>
</div>
</body>
</html>
