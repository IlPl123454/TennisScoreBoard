package org.plenkovii.service;

import org.plenkovii.dao.MatchDao;
import org.plenkovii.dto.MatchAppDto;
import org.plenkovii.entity.Match;
import org.plenkovii.entity.Player;

public class FinishedMatchesPersistenceService {
    MatchDao matchDao;

    public FinishedMatchesPersistenceService(MatchDao matchDao) {
        this.matchDao = matchDao;
    }

    public void saveFinishedMatch(MatchAppDto matchAppDto, String  winnerId) {
        Player player1 = matchAppDto.getPlayer1();
        Player player2 = matchAppDto.getPlayer2();
        Player matchWinner = winnerId.equals("1") ? player1 : player2;

        Match match = new Match(player1, player2, matchWinner);

        matchDao.save(match);
    }
}
