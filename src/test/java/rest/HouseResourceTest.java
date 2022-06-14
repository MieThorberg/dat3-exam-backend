package rest;

import com.google.gson.Gson;
import dtos.HouseDTO;
import dtos.RentalDTO;
import entities.House;
import entities.Rental;
import entities.Role;
import entities.User;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.*;

class HouseResourceTest {
    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    private static House h1, h2, h3;
    private static final Gson GSON = new Gson();

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    //This is how we hold on to the token after login, similar to that a client must store the token somewhere
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
        //System.out.println("TOKEN ---> " + securityToken);
    }

    @BeforeAll
    static void beforeAll() {
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
    static void afterAll() {
        //Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
    }

    // Setup the DataBase (used by the test-server and this test) in a known state BEFORE EACH TEST
    @BeforeEach
    void setUp() {
        EntityManager em = emf.createEntityManager();
        Role userRole = new Role("user");
        Role adminRole = new Role("admin");
        User user = new User("user", "test");
        user.addRole(userRole);
        User admin = new User("admin", "test");
        admin.addRole(adminRole);
        User both = new User("user_admin", "test");
        both.addRole(userRole);
        both.addRole(adminRole);
        h1 = new House("address1", "city1", 1);
        h2 = new House("address2", "city2", 2);
        h3 = new House("address3", "city3", 3);
        try {
            em.getTransaction().begin();
            em.createQuery("delete from User").executeUpdate();
            em.createQuery("delete from Role").executeUpdate();
            em.createNamedQuery("House.deleteAllRows").executeUpdate();

            em.persist(userRole);
            em.persist(adminRole);
            em.persist(user);
            em.persist(admin);
            em.persist(both);

            em.persist(h1);
            em.persist(h2);
            em.persist(h3);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testServerIsUp() {
        System.out.println("Testing is server UP");
        login("admin", "test");
        given()
                .header("x-access-token", securityToken)
                .when().get("/houses")
                .then().statusCode(200);
    }

    @Test
    void testLogRequest() {
        System.out.println("Testing logging request details");
        login("admin", "test");
        given().log().all()
                .header("x-access-token", securityToken)
                .when().get("/houses")
                .then().statusCode(200);
    }

    @Test
    void testLogResponse() {
        System.out.println("Testing logging response details");
        login("admin", "test");
        given()
                .header("x-access-token", securityToken)
                .when().get("/houses")
                .then().log().body().statusCode(200);
    }

    @Test
    void testGetAll() {
        System.out.println("Testing getAll()");
        List<HouseDTO> houseDTOs;
        login("admin", "test");
        houseDTOs = given()
                .contentType(ContentType.JSON)
                .header("x-access-token", securityToken)
                .when()
                .get("/houses")
                .then()
                .extract()
                .body()
                .jsonPath()
                .getList("", HouseDTO.class);

        HouseDTO houseDTO1 = new HouseDTO(h1);
        HouseDTO houseDTO2 = new HouseDTO(h2);
        HouseDTO houseDTO3 = new HouseDTO(h3);

        assertThat(houseDTOs, containsInAnyOrder(houseDTO1, houseDTO2, houseDTO3));
    }
}