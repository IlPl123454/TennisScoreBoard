package org.plenkovii.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.plenkovii.dto.MatchAppDto;
import org.plenkovii.dto.MatchScoreAppDto;
import org.plenkovii.dto.MatchViewDto;
import org.plenkovii.mapper.MatchMapper;
import org.plenkovii.service.MatchScoreCalculationService;
import org.plenkovii.service.OngoingMatchesService;
import org.plenkovii.validation.WinnerIdValidation;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

@WebServlet("/match-score")
public class MatchScoreServlet extends HttpServlet {

    private OngoingMatchesService ongoingMatchesService;

    @Override
    public void init() throws ServletException {
        ongoingMatchesService = (OngoingMatchesService) getServletContext().getAttribute("ongoingMatchesService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UUID uuid = UUID.fromString(req.getParameter("uuid"));

        MatchAppDto matchAppDto = ongoingMatchesService.getMatch(uuid);

        MatchViewDto matchViewDto = MatchMapper.toMatchViewDto(uuid, matchAppDto);

        req.setAttribute("match", matchViewDto);
        req.getRequestDispatcher("matchScore.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UUID uuid = UUID.fromString(req.getParameter("matchUuid"));

        String winnerId = req.getParameter("pointWinner");

        WinnerIdValidation.validate(winnerId);

        MatchAppDto match = ongoingMatchesService.getMatch(uuid);

        MatchScoreCalculationService matchScoreCalculationService = new MatchScoreCalculationService();

        matchScoreCalculationService.addPointToWinner(match, winnerId);

        if (isMatchOver(match)) {
            // Временное решение
            resp.setContentType("text/html;charset=UTF-8");
            PrintWriter out = resp.getWriter();
            out.println(matchOverPage);
        } else {
            resp.sendRedirect(req.getContextPath() + "/match-score?uuid=" + uuid.toString());
        }
    }

    private boolean isMatchOver(MatchAppDto match) {
        return match.getScore().getPlayer1Score().getSet() == MatchScoreAppDto.Set.TWO ||
                match.getScore().getPlayer2Score().getSet() == MatchScoreAppDto.Set.TWO;
    }

    private String matchOverPage = "<html>\n" +
            "<head>\n" +
            "    <title>Матч окончен</title>\n" +
            "</head>\n" +
            "<body>\n" +
            "<p><strong>Матч окончен, дальнейший функционал в разработке</strong> </p>\n" +
            "<form action=\"newMatch.jsp\" method=\"get\">\n" +
            "    <button type=\"submit\">Вернуться к созданию нового матча</button>\n" +
            "</form>\n" +
            "<h2></h2>\n" +
            "</body>\n" +
            "</html>";
}
