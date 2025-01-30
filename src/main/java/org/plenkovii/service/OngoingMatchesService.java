package org.plenkovii.service;

import org.plenkovii.dto.MatchAppDto;
import org.plenkovii.entity.Player;

import java.util.Map;
import java.util.UUID;

public class OngoingMatchesService {
    private final Map<UUID, MatchAppDto> matches;

    public OngoingMatchesService(Map<UUID, MatchAppDto> ongoingMatches) {
        this.matches = ongoingMatches;
    }

    public UUID saveNewMatch(String player1Name, String player2Name) {
        UUID uuid = UUID.randomUUID();
        MatchAppDto match = new MatchAppDto(new Player(player1Name), new Player(player2Name));
        matches.put(uuid, match);
        return uuid;
    }

    public void deleteMatch(UUID uuid) {
        matches.remove(uuid);
    }

    public MatchAppDto getMatch(UUID uuid) {
        return matches.get(uuid);
    }
}

