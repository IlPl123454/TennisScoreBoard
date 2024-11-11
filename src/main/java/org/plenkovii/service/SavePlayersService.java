package org.plenkovii.service;

import org.plenkovii.dao.PlayerDao;
import org.plenkovii.dto.PlayerRequestDto;
import org.plenkovii.entity.Player;

import java.util.Optional;

public class SavePlayersService {
    private final PlayerDao playerDao;

    public SavePlayersService(PlayerDao playerDao) {
        this.playerDao = playerDao;
    }

    public Player savePlayerIfNotPresent(PlayerRequestDto playerRequestDTO) {
        Optional<Player> optionalPlayer = playerDao.findByName(playerRequestDTO.getName());

        if (optionalPlayer.isPresent()) {
            return optionalPlayer.get();
        } else {
            //TODO: Проверить можно ли сделать в одну строчку
            Player newPlayer = new Player(playerRequestDTO.getName());
            playerDao.save(newPlayer);
            return newPlayer;
        }
    }
}
