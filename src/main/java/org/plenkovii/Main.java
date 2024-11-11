package org.plenkovii;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.plenkovii.entity.Match;
import org.plenkovii.entity.Player;
import org.plenkovii.util.HibernateUtil;

import java.util.List;


public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            Player player1 = new Player();
            player1.setName("Alex");
            session.save(player1);
            System.out.println("Player 1 saved");

            Player player2 = new Player();
            player2.setName("Mike");
            session.save(player2);
            System.out.println("Player 2 saved");


            Match match = new Match();
            match.setPlayer1(player1);
            match.setPlayer2(player2);
            match.setWinner(player1);
            session.flush();
            session.save(match);
            System.out.println("Match saved");

            transaction.commit();

            List<Player> players = session.createQuery("from Player", Player.class).list();
            players.forEach(System.out::println);

            List<Match> matches = session.createQuery("from Match", Match.class).list();
            for (Match m : matches) {
                System.out.println("Match ID: " + m.getId());
                System.out.println("Player 1: " + m.getPlayer1().getName());
                System.out.println("Player 2: " + m.getPlayer2().getName());
                System.out.println("Winner: " + m.getWinner().getName());
            }


            int i = 123;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        HibernateUtil.shutdown();
    }
}