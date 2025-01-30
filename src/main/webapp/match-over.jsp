<%@ page import="org.plenkovii.dto.MatchViewDto" %>
<%@ page import="org.plenkovii.dto.MatchScoreAppDto" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Матч окончен</title>
</head>
<body>
<jsp:include page="header.jsp"/>
<div class="final-score">
    <h2>Матч окончен, итоговый счет:</h2>

    <table class="final-score-table">
        <thead>
        <tr>
            <th class="player-name">Имя игрока</th>
            <th class="player-score">Выигранноые сеты</th>
        </tr>

        </thead>
        <tr>
            <td class="player-name">${match.player1Name}</td>
            <td class="player-score">${match.player1Sets}</td>
        </tr>
        <tr>
            <td class="player-name">${match.player2Name}</td>
            <td class="player-score">${match.player2Sets}</td>
        </tr>
    </table>

    <h2>Поздравляем с победой, ${winnerName}!</h2>
</div>
</body>
</html>
