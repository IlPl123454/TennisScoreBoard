<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Табло теннисного матча</title>
</head>
<body>
<jsp:include page="header.jsp"/>
<main>
    <div class="container">
        <form class="new-match" action="new-match.jsp" method="get">
            <button type="submit">Создать новый матч</button>
        </form>
    </div>
</main>
</body>
</html>
