<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Создание нового матча</title>
</head>
<body>
<jsp:include page="header.jsp"/>
<div class="container">
    <form action="new-match" method="post">
        <div class="input-group">
            <label for="player1">Игрок 1:</label>
            <input type="text" id="player1" name="player1" required><br><br>
        </div>
        <div class="input-group">
            <label for="player2">Игрок 2:</label>
            <input type="text" id="player2" name="player2" required><br><br>
        </div>
        <button type="submit">Создать матч</button>
    </form>
</div>

</body>
</html>
