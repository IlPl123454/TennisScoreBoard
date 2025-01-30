package org.plenkovii.dao;

import org.plenkovii.entity.Match;

import java.util.List;

public interface MatchDao extends CrudDao<Match> {

    List<Match> findByPlayerName(String name);

    List<Match> findAllWithPagination(int page, int pageSize);

    List<Match> findByPlayerNameWithPagination(String name, int page, int pageSize);
}
