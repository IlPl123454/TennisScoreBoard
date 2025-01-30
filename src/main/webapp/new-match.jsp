
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Создание нового матча</title>
</head>
<body>
  <form action="new-match" method="post">
    <label for="player1">Игрок 1:</label>
    <input type="text" id="player1" name="player1" required><br><br>
    <label for="player2">Игрок 2:</label>
    <input type="text" id="player2" name="player2" required><br><br>
    <button type="submit">Создать матч</button>
  </form>
</body>
</html>
