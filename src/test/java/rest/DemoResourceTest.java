package rest;

import com.google.gson.Gson;
import dtos.DemoDTO;
import entities.Demo;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
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
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

class DemoResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    private static Demo d1, d2, d3;
    private static final Gson GSON = new Gson();

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
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
        d1 = new Demo("name1");
        d2 = new Demo("name2");
        d3 = new Demo("name3");
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Demo.deleteAllRows").executeUpdate();
            em.persist(d1);
            em.persist(d2);
            em.persist(d3);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void testServerIsUp() {
        System.out.println("Testing is server UP");
        given().when().get("/demos").then().statusCode(200);
    }

    @Test
    public void testLogRequest() {
        System.out.println("Testing logging request details");
        given().log().all()
                .when().get("/demos")
                .then().statusCode(200);
    }

    @Test
    public void testLogResponse() {
        System.out.println("Testing logging response details");
        given()
                .when().get("/demos")
                .then().log().body().statusCode(200);
    }

    @Test
    public void testCreate() {
        System.out.println("Testing create()");

        String name = "name4";

        Demo demo = new Demo(name);
        DemoDTO demoDTO = new DemoDTO(demo);
        String requestBody = GSON.toJson(demoDTO);

        String expectedName = name;

        given()
                .contentType(ContentType.JSON)
                .and()
                .body(requestBody)
                .when()
                .post("/demos/create")
                .then()
                .assertThat()
                .statusCode(200)
                .body("id", notNullValue())
                .body("name", equalTo(expectedName));
    }

    @Test
    public void testUpdate() {
        System.out.println("Testing update()");

        String newName = "newName";

        DemoDTO demoDTO = new DemoDTO(d1);
        demoDTO.setName(newName);
        String requestBody = GSON.toJson(demoDTO);

        String expectedName = newName;

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .pathParam("id", d1.getId())
                .put("/demos/update/{id}")
                .then()
                .assertThat()
                .statusCode(200)
                .body("name", equalTo(expectedName));
    }

    @Test
    public void testDelete() {
        System.out.println("Testing delete()");

        String expectedName = "name1";

        given()
                .contentType(ContentType.JSON)
                .pathParam("id", d1.getId())
                .delete("/demos/delete/{id}")
                .then()
                .statusCode(200)
                .body("name", equalTo(expectedName));
    }

    @Test
    public void testGetAll() {
        System.out.println("Testing getAll()");
        List<DemoDTO> demoDTOs;

        demoDTOs = given()
                .contentType(ContentType.JSON)
                .when()
                .get("/demos")
                .then()
                .extract()
                .body()
                .jsonPath()
                .getList("", DemoDTO.class);

        DemoDTO demoDTO1 = new DemoDTO(d1);
        DemoDTO demoDTO2 = new DemoDTO(d2);
        DemoDTO demoDTO3 = new DemoDTO(d3);
        assertThat(demoDTOs, containsInAnyOrder(demoDTO1, demoDTO2, demoDTO3));
    }

    @Test
    public void testGetById() {
        System.out.println("Testing getById()");

        String expectedName = "name1";

        given()
                .contentType(ContentType.JSON)
                .get("/demos/id/{id}", d1.getId())
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("name", equalTo(expectedName));
    }


    @Test
    public void testGetByNotExistingId() {
        System.out.println("Testing getById() with not existing id");

        int expectedCode = 404;
        String expectedMessage = "Could not find a demo entity with id: 9999";

        given()
                .contentType(ContentType.JSON)
                .get("/demos/id/{id}", 9999)
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND_404.getStatusCode())
                .body("code", equalTo(expectedCode))
                .body("message", equalTo(expectedMessage));
    }
}