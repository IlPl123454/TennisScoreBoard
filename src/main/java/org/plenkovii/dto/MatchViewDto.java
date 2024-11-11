package org.plenkovii.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchViewDto {
    private UUID uuid;
    private boolean isTieBreak;
    private String player1Name;
    private String player2Name;
    private String player1Points;
    private String player2Points;
    private String player1Games;
    private String player2Games;
    private String player1Sets;
    private String player2Sets;
    private String player1TieBreakScore;
    private String player2TieBreakScore;
}
