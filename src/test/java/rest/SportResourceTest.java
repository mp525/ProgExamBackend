/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.SportDTO;
import dtos.SportTeamDTO;
import entities.Role;
import entities.Sport;
import entities.SportTeam;
import entities.User;
import facades.SportFacade;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.parsing.Parser;
import java.net.URI;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.hamcrest.Matchers;
import static org.hamcrest.Matchers.equalTo;
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
public class SportResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";

    private Sport s1;
    private Sport s2;
    private SportTeam t1;
    private SportTeam t2;

    public SportResourceTest() {
    }
    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;
    private final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final SportFacade facade = new SportFacade();

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();

        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void tearDownClass() {
        //Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.endREST_TestWithDB();

        httpServer.shutdownNow();
    }

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {

            s1 = new Sport("testName", "testDescription");
            s2 = new Sport("testName2", "testDescription2");
            t1 = new SportTeam(10.0, "team1", 1, 10);
            t2 = new SportTeam(10.0, "team2", 2, 20);

            s1.addTeam(t1);
            t2.setSport(s1);

            em.getTransaction().begin();
            em.createNamedQuery("SportTeam.deleteAllRows").executeUpdate();
            em.createNamedQuery("Sport.deleteAllRows").executeUpdate();
            em.createQuery("delete from User").executeUpdate();
            em.createQuery("delete from Role").executeUpdate();

            Role userRole = new Role("user");
            Role adminRole = new Role("admin");
            User user = new User("user", "test");
            user.addRole(userRole);
            User admin = new User("admin", "test");
            admin.addRole(adminRole);
            User both = new User("user_admin", "test");
            both.addRole(userRole);
            both.addRole(adminRole);
            em.persist(userRole);
            em.persist(adminRole);
            em.persist(user);
            em.persist(admin);
            em.persist(both);
            em.persist(s1);
            em.getTransaction().commit();
            
        } finally {
            em.close();
        }

    }

    private static String securityToken;

    //Utility method to login and set the returned securityToken
    private static void login(String role, String password) {
        String json = String.format("{username: \"%s\", password: \"%s\"}", role, password);
        securityToken = given()
                .contentType("application/json")
                .body(json)
                //.when().post("/api/login")
                .when().post("/login")
                .then()
                .extract().path("token");
    }

    private void logOut() {
        securityToken = null;
    }

    @Test
    public void serverIsRunning() {
        given().when().get("/sport").then().statusCode(200);
    }

    /**
     * Test of allSports method, of class SportResource.
     */
    @Test
    public void testAllSports() {
        
        List<SportDTO> list = given()
                .contentType("application/json")
                .when()
                .get("/sport/all")
                .then()
                .extract().body().jsonPath().getList("", SportDTO.class);
       
        assertEquals(list.get(0).getName(), s1.getName());

    }

    /**
     * Test of allSportTeams method, of class SportResource.
     */
    @Test
    public void testAllSportTeams() {
        List<SportTeamDTO> list = given()
                .contentType("application/json")
                .when()
                .get("/sport/all/teams")
                .then()
                .extract().body().jsonPath().getList("", SportTeamDTO.class);
        
        assertEquals(list.get(0).getTeamName(), t1.getTeamName());

    }

    /**
     * Test of addSport method, of class SportResource.
     */
     @Test
    public void testAddSport() {
        login("admin", "test");
        given()
                .contentType("application/json")
                .body(new SportDTO("testAddSport", "testAddDesc"))
                .header("x-access-token", securityToken)
                .when()
                .post("/sport")
                .then()
                .body("name", equalTo("testAddSport"));
    }

    /**
     * Test of addTeam method, of class SportResource.
     */
     @Test
    public void testAddTeam() {
        login("admin", "test");
        given()
                .contentType("application/json")
                .body(new SportTeamDTO(1.0, "testTeamNameAdd", 1, 2, "testName"))
                .header("x-access-token", securityToken)
                .when()
                .post("/sport/team")
                .then()
                .body("teamName", equalTo("testTeamNameAdd"));
    }

    /**
     * Test of editSportTeam method, of class SportResource.
     */
     @Test
    public void testEditSportTeam() {
        login("admin", "test");  
        given()
                .contentType("application/json")
                .body(new SportTeamDTO(t1.getId(), 1.2, "testEditTeam", 1, 2, "testName"))
                .header("x-access-token", securityToken)
                .when()
                .put("/sport/team")
                .then()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("teamName", equalTo("testEditTeam"));
                

    }

    /**
     * Test of deleteSportTeam method, of class SportResource.
     */
    @Test
    public void testDeleteSportTeam() {
         login("admin", "test");
        given()
                .contentType("application/json")
                .header("x-access-token", securityToken)
                .when()
                .delete("/sport/team/" + t1.getId())
                .then()
                .statusCode(HttpStatus.OK_200.getStatusCode());
    }

}
