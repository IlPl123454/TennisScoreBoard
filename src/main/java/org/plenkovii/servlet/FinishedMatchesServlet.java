package org.plenkovii.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.plenkovii.dao.HibernateMatchDao;
import org.plenkovii.dao.HibernatePlayerDao;
import org.plenkovii.dto.FinishedMatchDto;
import org.plenkovii.service.FinishedMatchesPersistenceService;

import java.io.IOException;
import java.util.List;

@WebServlet("/matches")
public class FinishedMatchesServlet extends HttpServlet {
    private FinishedMatchesPersistenceService finishedMatchesPersistenceService;

    @Override
    public void init() throws ServletException {
        super.init();
        finishedMatchesPersistenceService = new FinishedMatchesPersistenceService(new HibernatePlayerDao(),new HibernateMatchDao());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("filter_by_player_name");
        String pageString = req.getParameter("page");

        int page = pageString == null ? 1 : Integer.parseInt(pageString);
        req.setAttribute("page", page);

        List<FinishedMatchDto> matches = finishedMatchesPersistenceService.getFinishedMatches(name, page);
        req.setAttribute("matches", matches);

        int totalMatchesAmount = finishedMatchesPersistenceService.getMatchesAmount(name);
        int totalPages = (int) Math.ceil((double) totalMatchesAmount / finishedMatchesPersistenceService.PAGE_SIZE);
        req.setAttribute("totalPages", totalPages);

        req.setAttribute("name", name);

        req.getRequestDispatcher("matches.jsp").forward(req, resp);
    }
}
