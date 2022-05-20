package rest;

import entities.*;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Transient;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.jupiter.api.Assertions.*;

class GameResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

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


        User user = new User("user", "test123");
        User admin = new User("admin", "test123");
        User both = new User("user_admin", "test123");
        Player player = new Player(user);
        Player player1 = new Player(admin);
        Player player2 = new Player(both);
        player1.setHost(true);

        Game game = new Game(admin, 1234);
        player.setGame(game);
        player1.setGame(game);
        player2.setGame(game);
        game.setId(1L);

        Rule rule1 = new Rule("Assemble at least 7 players. An odd number of players is best, but it isn’t mandatory for a game");
        Rule rule2 = new Rule("The first phase of a game of Werewolf is the night round");
        Rule rule3 = new Rule("The werewolves chooses who they want to kill during the night/discussion round");
        Rule rule5 = new Rule("The discussion rounds lasts * min");
        Rule rule4 = new Rule("When the werewolves make a decision and agree on a victim they vote");
        Rule rule6 = new Rule("the voting rounds last * seconds or until all have voted");
        Rule rule7 = new Rule("If the villagers kill all the werewolves they win the game");
        Rule rule8 = new Rule("If the werewolves outnumber the villagers, they win the game");
        List<Rule> rules = new ArrayList<>();
        rules.add(rule1);
        rules.add(rule2);
        rules.add(rule3);
        rules.add(rule4);
        rules.add(rule5);
        rules.add(rule6);
        rules.add(rule7);
        rules.add(rule8);

        NightRound round = new NightRound(game);
        DayRound round1 = new DayRound(game);

        game.getNightRounds().add(round);
        game.getDayRounds().add(round1);


        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();


            Role userRole = new Role("user");
            Role adminRole = new Role("admin");
            admin.addRole(adminRole);
            both.addRole(userRole);
            both.addRole(adminRole);
            em.persist(userRole);
            em.persist(adminRole);
            em.persist(user);
            em.persist(admin);
            em.persist(both);
            em.persist(player);
            em.persist(player1);
            em.persist(player2);
            em.persist(game);
//    user.addRole(userRole);
            em.persist(round);
            em.persist(round1);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterAll
    public static void closeTestServer() {
        //System.in.read();

        //Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
    }

    // Setup the DataBase (used by the test-server and this test) in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the EntityClass used below to use YOUR OWN (renamed) Entity class
    @BeforeEach
    public void setUp() {

    }

    @AfterEach
    void tearDown() {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

//            em.createQuery("delete from DayRound").executeUpdate();
//            em.createQuery("delete from NightRound").executeUpdate();
//            em.createQuery("delete from Player ").executeUpdate();
//            em.createQuery("delete from Rule ").executeUpdate();
//            em.createQuery("delete from Game").executeUpdate();
//            em.createQuery("delete from User").executeUpdate();
//            em.createQuery("delete from Role").executeUpdate();

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

//    @Transient
//    @Test
//    void getAllGames() {
//        given()
//                .contentType("application/json")
//                .when()
//                .get("/games").then()
//                .statusCode(200)
//                .body("hostName", hasItem("admin"));
//    }
//
//    @Transient
//    @Test
//    void getGameById() {
//        given()
//                .contentType("application/json")
//                .when()
//                .get("/games/1").then()
//                .statusCode(200)
//                .body("hostName", equalTo("admin"));
//    }
//
//    @Test
//    void createGame() {
//        String json = "{gamepin: \"12345\"}";
//
//        given()
//                .contentType("application/json")
//                .when().body(json)
//                .post("/games/creategame/admin").then()
//                .statusCode(200)
//                .body("id", equalTo(2));
//    }

//    @Test
//    void createNightRound() {
//    }
//
//    @Test
//    void createDayRound() {
//    }
//
//    @Test
//    void createPlayer() {
//    }
//
//    @Test
//    void createPlayers() {
//    }
//
//    @Test
//    void getPlayersByGameId() {
//    }
//
//    @Test
//    void getAllVictims() {
//    }
//
//    @Test
//    void getLatestVictims() {
//    }
//
//    @Test
//    void getAlivePlayersByGameId() {
//    }
//
//    @Test
//    void getWerewolves() {
//    }
//
//    @Test
//    void getDays() {
//    }
//
//    @Test
//    void addDay() {
//    }
//
//    @Test
//    void getRounds() {
//    }
//
//    @Test
//    void testGetRounds() {
//    }
//
//    @Test
//    void getNightRoundsByDay() {
//    }
//
//    @Test
//    void getDayRoundsByDay() {
//    }
//
//    @Test
//    void nightRoundResult() {
//    }
//
//    @Test
//    void dayRoundResult() {
//    }
//
//    @Test
//    void getCurrentRound() {
//    }
//
//    @Test
//    void setPlayerVote() {
//    }
//
//    @Test
//    void getVoteResult() {
//    }
//
//    @Test
//    void cleanVotes() {
//    }
//
//    @Test
//    void killPlayer() {
//    }
//
//    @Test
//    void assignCharacters() {
//    }
//
//    @Test
//    void hasEnded() {
//    }
//
//    @Test
//    void getGameByPin() {
//    }
//
//    @Test
//    void setPlayerHost() {
//    }
//
//    @Test
//    void getAllRules() {
//    }
}