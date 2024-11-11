package org.plenkovii.util;

import org.plenkovii.dto.MatchScoreAppDto;

public class MatchScoreConverter {

    public static String getPointScore(MatchScoreAppDto.Point point) {
        return switch (point) {
            case LOVE -> "0";
            case FIFTEEN -> "15";
            case THIRTY -> "30";
            case FORTY -> "40";
            case DEUCE -> "Deuce";
            case ADVANTAGE -> "AD";
            case BLANK -> "";
        };
    }

    public static String getGameScore(MatchScoreAppDto.Game game) {
        return switch (game) {
            case ZERO -> "0";
            case ONE -> "1";
            case TWO -> "2";
            case THREE -> "3";
            case FOUR -> "4";
            case FIVE -> "5";
            case SIX -> "6";
            case SEVEN -> "7";
        };
    }

    public static String getSetScore(MatchScoreAppDto.Set set) {
        return switch (set) {
            case ZERO -> "0";
            case ONE -> "1";
            case TWO -> "2";
        };
    }

    public static String getTieBreakScore(int score) {
        return String.valueOf(score);
    }
}
