package org.plenkovii.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.plenkovii.service.OngoingMatchesService;
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

        OngoingMatchesService ongoingMatchesService = (OngoingMatchesService) getServletContext().getAttribute("ongoingMatchesService");

        UUID uuid = ongoingMatchesService.saveNewMatch(player1Name, player2Name);

        resp.sendRedirect(req.getContextPath() + "/match-score?uuid=" + uuid.toString());
    }
}
