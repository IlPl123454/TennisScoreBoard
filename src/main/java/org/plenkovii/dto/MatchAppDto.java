package org.plenkovii.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.plenkovii.entity.Player;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchAppDto {
    private Player player1;
    private Player player2;
    private PlayersScoreAppDto score;
    private boolean isOver;

    public MatchAppDto(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.score = new PlayersScoreAppDto();
    }
}
