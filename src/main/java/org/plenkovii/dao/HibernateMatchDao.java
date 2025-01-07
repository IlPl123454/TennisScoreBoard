package org.plenkovii.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.plenkovii.entity.Match;
import org.plenkovii.exception.DatabaseException;
import org.plenkovii.util.HibernateUtil;

import java.util.List;
import java.util.Optional;

public class HibernateMatchDao implements MatchDao {
    SessionFactory sessionFactory;

    public HibernateMatchDao() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    @Override
    public Match save(Match entity) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.save(entity);
            tx.commit();
            return entity;
        } catch (Exception e) {
            e.printStackTrace();
            if (tx != null) {
                tx.rollback();
            }
            throw new DatabaseException("Возникла ошибка при работе с базой данных");
        }
    }

    @Override
    public void update(Match entity) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.update(entity);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (tx != null) {
                tx.rollback();
            }
            throw new DatabaseException("Возникла ошибка при работе с базой данных");
        }

    }

    @Override
    public void delete(Match entity) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.delete(entity);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (tx != null) {
                tx.rollback();
            }
            throw new DatabaseException("Возникла ошибка при работе с базой данных");
        }

    }

    @Override
    public List<Match> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Match", Match.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            throw new DatabaseException("Возникла ошибка при работе с базой данных");
        }
    }

    @Override
    public Optional<Match> findById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.get(Match.class, id));
        } catch (Exception e) {
            e.printStackTrace();
            throw new DatabaseException("Возникла ошибка при работе с базой данных");
        }
    }

    @Override
    public List<Match> findByPlayerName(String name) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Match where player1.name = :name OR player2.name = :name", Match.class)
                    .setParameter("name", name).list();
        } catch (Exception e) {
            e.printStackTrace();
            throw new DatabaseException("Возникла ошибка при работе с базой данных");
        }
    }
}
