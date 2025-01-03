package org.plenkovii.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "matches")

public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "player1_id")
    private Player player1;
    @ManyToOne
    @JoinColumn(name = "player2_id")
    private Player player2;
    @ManyToOne
    @JoinColumn(name = "winner_id")
    private Player winner;
}
