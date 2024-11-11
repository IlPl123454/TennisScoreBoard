package org.plenkovii.dao;

import org.plenkovii.entity.Match;

import java.util.List;

public interface MatchDao extends CrudDao<Match> {
    List<Match> findByPlayerName(String name);
}