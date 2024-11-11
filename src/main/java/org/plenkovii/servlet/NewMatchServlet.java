package org.plenkovii.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.plenkovii.dao.HibernatePlayerDao;
import org.plenkovii.dao.PlayerDao;
import org.plenkovii.dto.PlayerRequestDto;
import org.plenkovii.entity.Player;
import org.plenkovii.service.OngoingMatchesService;
import org.plenkovii.service.SavePlayersService;
import org.plenkovii.validation.PlayerNameValidation;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/new-match")
public class NewMatchServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String player1Name = req.getParameter("player1");
        String player2Name = req.getParameter("player2");

        PlayerNameValidation.validate(player1Name, player2Name);

        PlayerRequestDto player1Dto = new PlayerRequestDto(player1Name);
        PlayerRequestDto player2Dto = new PlayerRequestDto(player2Name);

        PlayerDao playerDao = new HibernatePlayerDao();
        SavePlayersService savePlayersService = new SavePlayersService(playerDao);

        Player player1 = savePlayersService.savePlayerIfNotPresent(player1Dto);
        Player player2 = savePlayersService.savePlayerIfNotPresent(player2Dto);

        OngoingMatchesService ongoingMatchesService = (OngoingMatchesService) getServletContext().getAttribute("ongoingMatchesService");
        UUID uuid = ongoingMatchesService.saveNewMatch(player1, player2);

        resp.sendRedirect(req.getContextPath() + "/match-score?uuid=" + uuid.toString());
    }
}
