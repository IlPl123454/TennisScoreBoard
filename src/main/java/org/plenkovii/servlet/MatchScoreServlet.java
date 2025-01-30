package org.plenkovii.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.plenkovii.dao.HibernateMatchDao;
import org.plenkovii.dao.HibernatePlayerDao;
import org.plenkovii.dao.MatchDao;
import org.plenkovii.dao.PlayerDao;
import org.plenkovii.dto.MatchAppDto;
import org.plenkovii.dto.MatchScoreAppDto;
import org.plenkovii.dto.MatchViewDto;
import org.plenkovii.mapper.MatchMapper;
import org.plenkovii.service.FinishedMatchesPersistenceService;
import org.plenkovii.service.MatchScoreCalculationService;
import org.plenkovii.service.OngoingMatchesService;
import org.plenkovii.validation.OngoingMatchValidation;
import org.plenkovii.validation.WinnerIdValidation;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/match-score")
public class MatchScoreServlet extends HttpServlet {

    private OngoingMatchesService ongoingMatchesService;
    private MatchScoreCalculationService matchScoreCalculationService;
    private FinishedMatchesPersistenceService finishedMatchesPersistenceService;

    @Override
    public void init() throws ServletException {
        super.init();

        ongoingMatchesService = (OngoingMatchesService) getServletContext().getAttribute("ongoingMatchesService");
        finishedMatchesPersistenceService = (FinishedMatchesPersistenceService) getServletContext()
                .getAttribute("finishedMatchesPersistenceService");
        matchScoreCalculationService = new MatchScoreCalculationService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UUID uuid = UUID.fromString(req.getParameter("uuid"));

        OngoingMatchValidation.validateUuid(ongoingMatchesService, uuid);

        MatchAppDto matchAppDto = ongoingMatchesService.getMatch(uuid);

        MatchViewDto matchViewDto = MatchMapper.matchAppDtotoMatchViewDto(uuid, matchAppDto);

        MatchScoreAppDto.Set set = matchAppDto.getScore().getPlayer1Score().getSet();

        matchScoreCalculationService.updateIsMatchOver(matchAppDto);

        req.setAttribute("match", matchViewDto);

        if (matchAppDto.isOver()) {
            String winnerName = getWinnerName(matchAppDto);
            req.setAttribute("winnerName", winnerName);
            req.getRequestDispatcher("match-over.jsp").forward(req, resp);
            ongoingMatchesService.deleteMatch(uuid);
        } else {
            req.getRequestDispatcher("match-score.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UUID uuid = UUID.fromString(req.getParameter("matchUuid"));
        String winnerId = req.getParameter("pointWinner");

        WinnerIdValidation.validate(winnerId);

        MatchAppDto matchAppDto = ongoingMatchesService.getMatch(uuid);

        matchScoreCalculationService.addPointToWinner(matchAppDto, winnerId);
        matchScoreCalculationService.updateIsMatchOver(matchAppDto);

        if (isMatchOver(matchAppDto)) {
            finishedMatchesPersistenceService.saveFinishedMatch(matchAppDto, winnerId);
        }

        resp.sendRedirect(req.getContextPath() + "/match-score?uuid=" + uuid);
    }

    private boolean isMatchOver(MatchAppDto match) {
        return match.getScore().getPlayer1Score().getSet() == MatchScoreAppDto.Set.TWO ||
                match.getScore().getPlayer2Score().getSet() == MatchScoreAppDto.Set.TWO;
    }

    private String getWinnerName(MatchAppDto match) {
        if (match.getScore().getPlayer1Score().getSet() == MatchScoreAppDto.Set.TWO) {
            return match.getPlayer1().getName();
        } else {
            return match.getPlayer2().getName();
        }
    }
}
