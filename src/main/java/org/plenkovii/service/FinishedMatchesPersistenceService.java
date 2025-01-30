package org.plenkovii.service;

import lombok.Data;
import org.plenkovii.dao.MatchDao;
import org.plenkovii.dao.PlayerDao;
import org.plenkovii.dto.FinishedMatchDto;
import org.plenkovii.dto.MatchAppDto;
import org.plenkovii.entity.Match;
import org.plenkovii.entity.Player;
import org.plenkovii.mapper.MatchMapper;

import java.util.ArrayList;
import java.util.List;

public class FinishedMatchesPersistenceService {
    PlayerDao playerDao;
    MatchDao matchDao;
    public final int PAGE_SIZE = 7;

    public FinishedMatchesPersistenceService(PlayerDao playerDao ,MatchDao matchDao) {
        this.playerDao = playerDao;
        this.matchDao = matchDao;
    }

    public void saveFinishedMatch(MatchAppDto matchAppDto, String winnerId) {
        Player player1 = matchAppDto.getPlayer1();
        playerDao.save(player1);

        Player player2 = matchAppDto.getPlayer2();
        playerDao.save(player2);

        Player matchWinner = winnerId.equals("1") ? player1 : player2;

        Match match = new Match(player1, player2, matchWinner);
        matchDao.save(match);
    }

    public List<FinishedMatchDto> getFinishedMatches(String name, int page) {
        List<Match> matches;
        List<FinishedMatchDto> finishedMatchDtos = new ArrayList<>();

        if (name != null) {
            matches = matchDao.findByPlayerNameWithPagination(name, page, PAGE_SIZE);
        } else {
            matches = matchDao.findAllWithPagination(page, PAGE_SIZE);
        }

        for (Match match : matches) {
            FinishedMatchDto finishedMatchDto = MatchMapper.MatchEntityToFinishedMatchDto(match);
            finishedMatchDtos.add(finishedMatchDto);
        }

        return finishedMatchDtos;
    }

    public int getMatchesAmount(String name) {
        if (name != null) {
            return matchDao.findByPlayerName(name).size();
        } else {
            return matchDao.findAll().size();
        }
    }

}
