/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import utils.EMF_Creator;

/**
 *
 * @author Mathias
 */
public class Tester {

    public static void main(String[] args) {
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        
//        One one = new One(new ArrayList(), "Test1");
//        Many many = new Many("Test1");
//        one.addMany(many);
        
        Sport testSport = new Sport("Basketball", "Ball game");
        Sport testSport2 = new Sport("Tennis", "Ball game");
        SportTeam testTeam = new SportTeam(10.0, "A-team", 10, 30);
        SportTeam testTeam2 = new SportTeam(20.0, "B-team", 10, 30);
        Player p1 = new Player("Blomst", "blomst@email.com", 12121212, 10);
        Player p2 = new Player("Boble", "boble@email.com", 21212121, 20);
        Player p3 = new Player("Belis", "belis@email.com", 26712121, 30);
        
        testSport.addTeam(testTeam);
        testSport2.addTeam(testTeam2);
        
        testTeam.addPlayer(p1);
        testTeam.addPlayer(p2);
        testTeam2.addPlayer(p2);
        testTeam2.addPlayer(p3);

        em.getTransaction().begin();
        em.persist(p1);
        em.persist(p2);
        em.persist(p3);
        em.persist(testSport);
        em.persist(testSport2);
        em.getTransaction().commit();
        
    }

}
