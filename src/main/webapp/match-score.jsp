<%@ page import="org.plenkovii.dto.MatchViewDto" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  MatchViewDto match = (MatchViewDto) request.getAttribute("match");
  String player1DisplayPoints;
  String player2DisplayPoints;

  if (match.getPlayer1Games().equals("6") && match.getPlayer2Games().equals("6")) {
    player1DisplayPoints = match.getPlayer1TieBreakScore();
    player2DisplayPoints = match.getPlayer2TieBreakScore();
  } else {
    player1DisplayPoints = match.getPlayer1Points();
    player2DisplayPoints = match.getPlayer2Points();
  }
%>
<html>
<head>
    <title>Счет матча</title>
</head>
<body>
  <table>
    <tr>
      <td>${match.player1Name}</td>
      <td>${match.player1Sets}</td>
      <td>${match.player1Games}</td>
      <td><%= player1DisplayPoints %></td>
      <td>
        <form action="match-score" method="post">
          <input type="hidden" name="matchUuid" value="${match.uuid}">
          <input type="hidden" name="pointWinner" value="1">
          <button type="submit">+</button>
        </form>
      </td>
    </tr>
    <tr>
      <td>${match.player2Name}</td>
      <td>${match.player2Sets}</td>
      <td>${match.player2Games}</td>
      <td><%= player2DisplayPoints %></td>
      <td>
        <form action="match-score" method="post">
          <input type="hidden" name="matchUuid" value="${match.uuid}">
          <input type="hidden" name="pointWinner" value="2">
          <button type="submit">+</button>
        </form>
      </td>
    </tr>
  </table>
</body>
</html>
