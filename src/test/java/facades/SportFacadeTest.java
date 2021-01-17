/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dtos.SportDTO;
import dtos.SportTeamDTO;
import entities.Sport;
import entities.SportTeam;
import errorhandling.MissingInputException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import utils.EMF_Creator;

/**
 *
 * @author Mathias
 */
public class SportFacadeTest {

    private static EntityManagerFactory emf;
    private static SportFacade facade = new SportFacade();

    private Sport s1;
    private Sport s2;
    private SportTeam t1;
    private SportTeam t2;

    public SportFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = SportFacade.getSportFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();

        s1 = new Sport("testName", "testDescription");
        s2 = new Sport("testName2", "testDescription2");
        t1 = new SportTeam(10.0, "team1", 1, 10);
        t2 = new SportTeam(10.0, "team2", 2, 20);
        
        s1.addTeam(t1);
        t2.setSport(s1);
        
        try {
            em.getTransaction().begin();
            em.createNamedQuery("SportTeam.deleteAllRows").executeUpdate();
            em.createNamedQuery("Sport.deleteAllRows").executeUpdate();
            em.persist(s1);
            em.getTransaction().commit();
        } finally {
            em.close();
        }

    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of addSport method, of class SportFacade.
     */
    @Test
    public void testAddSport() throws MissingInputException {
        SportDTO dto = new SportDTO(s2);
        System.out.println(dto.getName());
        SportDTO result = facade.addSport(dto);
        assertEquals(dto.getName(), result.getName());
    }
    
    @Test
    public void testAddSportTeam() throws MissingInputException {
        SportTeamDTO dto = new SportTeamDTO(t2);
        SportTeamDTO result = facade.addSportTeam(dto);
        assertEquals(dto.getTeamName(), result.getTeamName());
    }
    
    @Test
    public void testGetAllSports(){
        List<SportDTO> list = facade.allSports();
        assertEquals(list.get(0).getName(),s1.getName());
    }
    
    @Test
    public void testGetAllSportTeams(){
        List<SportTeamDTO> list = facade.allSportTeams();
        assertEquals(list.get(0).getTeamName(),t1.getTeamName());
    }
    
    @Test
    public void testDeleteSportTeam(){
        int id = t1.getId();
        facade.deleteSportTeam(id);
        List<SportTeamDTO> list = facade.allSportTeams();
        assertTrue(list.isEmpty());
    }
    
    @Test
    public void testEditTeams() throws MissingInputException{
        SportTeamDTO dto = new SportTeamDTO(t1);
        dto.setTeamName("editedTeamName");
        SportTeamDTO dto2 = facade.editSportTeam(dto);
        assertEquals(dto.getTeamName(), dto2.getTeamName());
    }

}
