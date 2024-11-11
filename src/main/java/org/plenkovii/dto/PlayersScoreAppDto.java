package org.plenkovii.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayersScoreAppDto {
    private MatchScoreAppDto player1Score = new MatchScoreAppDto();
    private MatchScoreAppDto player2Score = new MatchScoreAppDto();
    boolean isBalance = false;
    boolean isTieBreak = false;
}
