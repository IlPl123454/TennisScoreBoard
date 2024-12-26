package org.plenkovii.service;

import lombok.Data;
import org.plenkovii.dto.MatchAppDto;
import org.plenkovii.dto.MatchScoreAppDto;

@Data
public class MatchScoreCalculationService {
    private boolean isBalance = false;
    private boolean isTieBreak = false;

    public void addPointToWinner(MatchAppDto matchAppDto, String winner) {
        MatchScoreAppDto winnerPlayerScore = getWinnerScore(matchAppDto, winner);
        MatchScoreAppDto looserPlayerScore = getLooserScore(matchAppDto, winner);
        updateBalanceStatus(winnerPlayerScore, looserPlayerScore);
        updateTieBreakStatus(matchAppDto);


        if (isGameOver(winnerPlayerScore, looserPlayerScore)) {
            setBalance(false);
            addGame(winnerPlayerScore);
            winnerPlayerScore.setPoint(MatchScoreAppDto.Point.LOVE);
            looserPlayerScore.setPoint(MatchScoreAppDto.Point.LOVE);
            winnerPlayerScore.setTieBreakPoint(0);
            looserPlayerScore.setTieBreakPoint(0);

            if (isSetOver(winnerPlayerScore, looserPlayerScore)) {
                addSet(winnerPlayerScore);
                winnerPlayerScore.setGame(MatchScoreAppDto.Game.ZERO);
                looserPlayerScore.setGame(MatchScoreAppDto.Game.ZERO);
            }
        } else {
            addPoint(winnerPlayerScore, looserPlayerScore);
        }
    }

    private void updateBalanceStatus(MatchScoreAppDto winnerPlayerScore, MatchScoreAppDto looserPlayerScore) {
        if ((winnerPlayerScore.getPoint() == MatchScoreAppDto.Point.FORTY &&
                looserPlayerScore.getPoint() == MatchScoreAppDto.Point.FORTY) ||

                winnerPlayerScore.getPoint() == MatchScoreAppDto.Point.DEUCE ||
                winnerPlayerScore.getPoint() == MatchScoreAppDto.Point.BLANK) {
            this.isBalance = true;
        }
    }

    public void updateTieBreakStatus(MatchAppDto matchAppDto) {
        if (matchAppDto.getScore().getPlayer1Score().getGame() == MatchScoreAppDto.Game.SIX &&
                matchAppDto.getScore().getPlayer2Score().getGame() == MatchScoreAppDto.Game.SIX) {
            this.isTieBreak = true;
        }
    }


    private boolean isGameOver(MatchScoreAppDto winnerPlayerScore, MatchScoreAppDto looserPlayerScore) {
        if (isTieBreak) {
            return winnerPlayerScore.getTieBreakPoint() > 5 &&
                    (winnerPlayerScore.getTieBreakPoint() - looserPlayerScore.getTieBreakPoint() > 0);
        } else {
            return ((!isBalance && winnerPlayerScore.getPoint() == MatchScoreAppDto.Point.FORTY) ||
                    winnerPlayerScore.getPoint() == MatchScoreAppDto.Point.ADVANTAGE);
        }
    }

    private boolean isSetOver(MatchScoreAppDto winnerPlayerScore, MatchScoreAppDto looserPlayerScore) {


        return (winnerPlayerScore.getGame() == MatchScoreAppDto.Game.SEVEN ||
                (winnerPlayerScore.getGame() == MatchScoreAppDto.Game.SIX &&
                        looserPlayerScore.getGame() != MatchScoreAppDto.Game.FIVE &&
                        looserPlayerScore.getGame() != MatchScoreAppDto.Game.SIX));
    }


    public boolean isMatchOver(MatchAppDto matchAppDto) {
        return matchAppDto.getScore().getPlayer1Score().getSet() == MatchScoreAppDto.Set.TWO ||
                matchAppDto.getScore().getPlayer2Score().getSet() == MatchScoreAppDto.Set.TWO;
    }

    private void addPoint(MatchScoreAppDto winnerPlayerScore, MatchScoreAppDto looserPlayerScore) {
        if (this.isBalance) {
            switch (winnerPlayerScore.getPoint()) {
                case FORTY, DEUCE:
                    winnerPlayerScore.setPoint(MatchScoreAppDto.Point.ADVANTAGE);
                    looserPlayerScore.setPoint(MatchScoreAppDto.Point.BLANK);
                    break;
                case BLANK:
                    winnerPlayerScore.setPoint(MatchScoreAppDto.Point.DEUCE);
                    looserPlayerScore.setPoint(MatchScoreAppDto.Point.DEUCE);
                    break;
                default:
                    winnerPlayerScore.setPoint(getNextPoint(winnerPlayerScore));
            }
        } else if (this.isTieBreak) {
            addTieBreakPoint(winnerPlayerScore);
        } else {
            winnerPlayerScore.setPoint(getNextPoint(winnerPlayerScore));
        }
    }

    private void addTieBreakPoint(MatchScoreAppDto winnerPlayerScore) {
        winnerPlayerScore.setTieBreakPoint(winnerPlayerScore.getTieBreakPoint() + 1);
    }

    private void addGame(MatchScoreAppDto matchScoreAppDto) {
        matchScoreAppDto.setGame(getNextGame(matchScoreAppDto));
    }

    private void addSet(MatchScoreAppDto matchScoreAppDto) {
        matchScoreAppDto.setSet(getNextSet(matchScoreAppDto));
    }

    private MatchScoreAppDto.Point getNextPoint(MatchScoreAppDto matchScoreAppDto) {
        return getNextScoreUnit(matchScoreAppDto.getPoint(), MatchScoreAppDto.Point.class);
    }

    private MatchScoreAppDto.Game getNextGame(MatchScoreAppDto matchScoreAppDto) {
        return getNextScoreUnit(matchScoreAppDto.getGame(), MatchScoreAppDto.Game.class);
    }

    private MatchScoreAppDto.Set getNextSet(MatchScoreAppDto matchScoreAppDto) {
        return getNextScoreUnit(matchScoreAppDto.getSet(), MatchScoreAppDto.Set.class);
    }


    private <T extends Enum<T>> T getNextScoreUnit(T scoreUnit, Class<T> enumClass) {
        T[] scoreUnits = enumClass.getEnumConstants();

        int currentOrdinal = scoreUnit.ordinal();
        int nextOrdinal = currentOrdinal + 1;

        return scoreUnits[nextOrdinal];
    }

    private MatchScoreAppDto getWinnerScore(MatchAppDto matchAppDto, String winner) {
        return winner.equals("1") ? matchAppDto.getScore().getPlayer1Score() : matchAppDto.getScore().getPlayer2Score();
    }

    private MatchScoreAppDto getLooserScore(MatchAppDto matchAppDto, String winner) {
        return winner.equals("1") ? matchAppDto.getScore().getPlayer2Score() : matchAppDto.getScore().getPlayer1Score();
    }
}