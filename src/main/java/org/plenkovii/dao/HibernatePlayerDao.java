package org.plenkovii.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.plenkovii.entity.Player;
import org.plenkovii.exception.DatabaseException;
import org.plenkovii.util.HibernateUtil;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class HibernatePlayerDao implements PlayerDao {
    private final SessionFactory sessionFactory;

    public HibernatePlayerDao() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    @Override
    public List<Player> findAll() {
        Transaction transaction = null;
        try (Session session = sessionFactory.getCurrentSession()) {
            transaction = session.beginTransaction();
            List<Player> players = session.createQuery("from Player").list();
            transaction.commit();
            return players;
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) transaction.rollback();
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<Player> findById(int id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.getCurrentSession()) {
            transaction = session.beginTransaction();
            Optional<Player> player = Optional.ofNullable(session.get(Player.class, id));
            transaction.commit();
            return player;
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) transaction.rollback();
            throw new DatabaseException("Возникла ошибка при работе с базой данных");
        }
    }

    @Override
    public Optional<Player> findByName(String name) {
        Transaction transaction = null;
        try (Session session = sessionFactory.getCurrentSession()) {
            transaction = session.beginTransaction();
            Optional<Player> player = session.createQuery("from Player where name = :name", Player.class)
                    .setParameter("name", name)
                    .uniqueResultOptional();
            transaction.commit();
            return player;
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) transaction.rollback();
            throw new DatabaseException("Возникла ошибка при работе с базой данных");
        }
    }

    @Override
    public Player save(Player entity) {
        Transaction transaction = null;
        try (Session session = sessionFactory.getCurrentSession()) {
            transaction = session.beginTransaction();
            session.save(entity);
            transaction.commit();
            return entity;
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) transaction.rollback();
            throw new DatabaseException("Возникла ошибка при работе с базой данных");
        }
    }

    @Override
    public Player saveIfNotExists(Player player) {
        Optional<Player> playerOptional = findByName(player.getName());

        if (playerOptional.isPresent()) {
            return playerOptional.get();
        } else {
            return save(player);
        }

    }

    @Override
    public void update(Player entity) {
        Transaction transaction = null;
        try (Session session = sessionFactory.getCurrentSession()) {
            transaction = session.beginTransaction();
            session.update(entity);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) transaction.rollback();
            throw new DatabaseException("Возникла ошибка при работе с базой данных");
        }
    }

    @Override
    public void delete(Player entity) {
        Transaction transaction = null;
        try (Session session = sessionFactory.getCurrentSession()) {
            transaction = session.beginTransaction();
            session.delete(entity);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) transaction.rollback();
            throw new DatabaseException("Возникла ошибка при работе с базой данных");
        }
    }
}
