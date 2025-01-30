package org.plenkovii.dao;

import org.plenkovii.entity.Player;

import java.net.DatagramSocketImplFactory;
import java.util.Optional;

public interface PlayerDao extends CrudDao<Player> {
    Optional<Player> findByName(String name);
    Player saveIfNotExists(Player player);
}
