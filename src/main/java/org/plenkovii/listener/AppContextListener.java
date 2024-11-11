package org.plenkovii.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.plenkovii.dto.MatchAppDto;
import org.plenkovii.service.OngoingMatchesService;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@WebListener
public class AppContextListener implements ServletContextListener {
    public void contextInitialized(ServletContextEvent sce) {
        Map<UUID, MatchAppDto> matches = new HashMap<>();

        OngoingMatchesService ongoingMatchesService = new OngoingMatchesService(matches);

        sce.getServletContext().setAttribute("ongoingMatchesService", ongoingMatchesService);
    }
}
