package org.plenkovii.dto;

import lombok.Data;

@Data
public class MatchScoreAppDto {
    private Point point = Point.LOVE;
    private Game game = Game.ZERO;
    private Set set = Set.ZERO;
    private int tieBreakPoint = 0;



    public enum Point {
        LOVE,
        FIFTEEN,
        THIRTY,
        FORTY,
        DEUCE,
        ADVANTAGE,
        BLANK
    }

    public enum Game {
        ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN
    }

    public enum Set {
        ZERO, ONE, TWO
    }
}
