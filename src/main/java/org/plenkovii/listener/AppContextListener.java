package org.plenkovii.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.plenkovii.dao.HibernateMatchDao;
import org.plenkovii.dao.HibernatePlayerDao;
import org.plenkovii.dao.MatchDao;
import org.plenkovii.dao.PlayerDao;
import org.plenkovii.dto.MatchAppDto;
import org.plenkovii.exception.DatabaseException;
import org.plenkovii.service.FinishedMatchesPersistenceService;
import org.plenkovii.service.OngoingMatchesService;
import org.plenkovii.util.HibernateUtil;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@WebListener
public class AppContextListener implements ServletContextListener {
    public void contextInitialized(ServletContextEvent sce) {
        databaseInit();
        ongoingMatchesInit(sce);
        finishedMatchesPersistenceServiceInit(sce);
    }

    private void ongoingMatchesInit(ServletContextEvent sce) {
        Map<UUID, MatchAppDto> matches = new HashMap<>();
        OngoingMatchesService ongoingMatchesService = new OngoingMatchesService(matches);

        sce.getServletContext().setAttribute("ongoingMatchesService", ongoingMatchesService);
    }

    private void finishedMatchesPersistenceServiceInit(ServletContextEvent sce) {
        PlayerDao playerDao = new HibernatePlayerDao();
        MatchDao matchDao = new HibernateMatchDao();

        FinishedMatchesPersistenceService finishedMatchesPersistenceService = new FinishedMatchesPersistenceService(playerDao, matchDao);
        sce.getServletContext().setAttribute("finishedMatchesPersistenceService", finishedMatchesPersistenceService);
    }



    private void databaseInit() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();

            String sqlScript1 = Files.readString(Paths.get(getClass().getClassLoader().getResource("db/schema.sql").toURI()));

            // Разделяем скрипт на отдельные SQL-запросы
            String[] queries1 = sqlScript1.split(";");

            // Выполняем каждый запрос
            for (String query : queries1) {
                if (!query.trim().isEmpty()) { // Пропускаем пустые строки
                    NativeQuery<?> nativeQuery = session.createNativeQuery(query);
                    nativeQuery.executeUpdate();
                }
            }

            String sqlScript2 = Files.readString(Paths.get(getClass().getClassLoader().getResource("db/data.sql").toURI()));

            // Разделяем скрипт на отдельные SQL-запросы
            String[] queries2 = sqlScript2.split(";");

            // Выполняем каждый запрос
            for (String query : queries2) {
                if (!query.trim().isEmpty()) { // Пропускаем пустые строки
                    NativeQuery<?> nativeQuery = session.createNativeQuery(query);
                    nativeQuery.executeUpdate();
                }
            }
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (tx != null) {
                tx.rollback();
            }
            throw new DatabaseException("Возникла ошибка при работе с базой данных");
        }
    }
}
