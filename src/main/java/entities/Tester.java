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
        
        Sport testSport = new Sport("testSport", "test");
        SportTeam testTeam = new SportTeam(10.0, "testTeam", 10, 11);
        Player p1 = new Player("navn1", "email1", 12121212, 20);
        Player p2 = new Player("navn2", "email2", 21212121, 20);
        
        testSport.addTeam(testTeam);
        
        testTeam.addPlayer(p1);
        testTeam.addPlayer(p2);

        em.getTransaction().begin();
        em.persist(p1);
        em.persist(p2);
        em.persist(testSport);
        em.getTransaction().commit();
        
    }

}
