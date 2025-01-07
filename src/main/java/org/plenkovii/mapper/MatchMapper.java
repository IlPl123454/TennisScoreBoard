package org.plenkovii.mapper;

import org.plenkovii.dto.MatchAppDto;
import org.plenkovii.dto.MatchViewDto;
import org.plenkovii.util.MatchScoreConverter;

import java.util.UUID;

public class MatchMapper {
    public static MatchViewDto matchAppDtotoMatchViewDto(UUID uuid, MatchAppDto matchAppDto) {
        return new MatchViewDto(
                uuid,
                matchAppDto.getScore().isTieBreak(),
                matchAppDto.getPlayer1().getName(),
                matchAppDto.getPlayer2().getName(),
                MatchScoreConverter.getPointScore(matchAppDto.getScore().getPlayer1Score().getPoint()),
                MatchScoreConverter.getPointScore(matchAppDto.getScore().getPlayer2Score().getPoint()),
                MatchScoreConverter.getGameScore(matchAppDto.getScore().getPlayer1Score().getGame()),
                MatchScoreConverter.getGameScore(matchAppDto.getScore().getPlayer2Score().getGame()),
                MatchScoreConverter.getSetScore(matchAppDto.getScore().getPlayer1Score().getSet()),
                MatchScoreConverter.getSetScore(matchAppDto.getScore().getPlayer2Score().getSet()),
                MatchScoreConverter.getTieBreakScore(matchAppDto.getScore().getPlayer1Score().getTieBreakPoint()),
                MatchScoreConverter.getTieBreakScore(matchAppDto.getScore().getPlayer2Score().getTieBreakPoint())
        );
    }
}
