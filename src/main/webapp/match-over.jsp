<%@ page import="org.plenkovii.dto.MatchViewDto" %>
<%@ page import="org.plenkovii.dto.MatchScoreAppDto" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Матч окончен</title>
</head>
<body>
<p>Матч окончен, итоговый счет:</p>

<table>
    <tr>
        <td>${match.player1Name}</td>
        <td>${match.player1Sets}</td>
    </tr>
    <tr>
        <td>${match.player2Name}</td>
        <td>${match.player2Sets}</td>
    </tr>
</table>
<p>Поздравляем с победой, ${winnerName}!</p>
</body>
</html>
