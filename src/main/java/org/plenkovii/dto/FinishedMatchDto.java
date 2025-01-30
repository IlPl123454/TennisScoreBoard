package org.plenkovii.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class FinishedMatchDto {
    private String player1Name;
    private String player2Name;
    private String winnerName;
}
