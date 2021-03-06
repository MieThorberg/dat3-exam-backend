package rest;

import com.google.gson.Gson;
import dtos.HouseDTO;
import dtos.RentalDTO;
import entities.*;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

class RentalResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    private static Rental r1, r2, r3;
    private static House h1, h2, h3;
    private static Tenant t1, t2, t3;
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
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Rental.deleteAllRows").executeUpdate();
            em.createQuery("delete from User").executeUpdate();
            em.createQuery("delete from Role").executeUpdate();
            em.createNamedQuery("House.deleteAllRows").executeUpdate();

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
            t1 = new Tenant("name1", "phone1", "job1");
            t2 = new Tenant("name2", "phone2", "job2");
            t3 = new Tenant("name3", "phone3", "job3");
            em.persist(t1);
            em.persist(t2);
            em.persist(t3);

            Set<Tenant> tenants = new HashSet<>();
            tenants.add(t1);
            r1 = new Rental("01-01-2001", "01-01-2002", 100, 100, "Alice", h1, tenants);
            r2 = new Rental("02-02-2002", "02-02-2003", 200, 200, "Bob", h2, tenants);
            r3 = new Rental("03-03-2003", "03-03-2004", 300, 300, "Charlie",h3, tenants);

            em.persist(userRole);
            em.persist(adminRole);
            em.persist(user);
            em.persist(admin);
            em.persist(both);
            em.persist(h1);
            em.persist(h2);
            em.persist(h3);
            em.persist(r1);
            em.persist(r2);
            em.persist(r3);
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
                .when().get("/rentals")
                .then().statusCode(200);
    }

    @Test
    void testLogRequest() {
        System.out.println("Testing logging request details");
        login("admin", "test");
        given().log().all()
                .header("x-access-token", securityToken)
                .when().get("/rentals")
                .then().statusCode(200);
    }

    @Test
    void testLogResponse() {
        System.out.println("Testing logging response details");
        login("admin", "test");
        given()
                .header("x-access-token", securityToken)
                .when().get("/rentals")
                .then().log().body().statusCode(200);
    }

    @Test
    void testCreate() {
        System.out.println("Testing create()");

        String startDate = "04-04-2004";
        String endDate = "04-04-2005";
        int priceAnnual = 400;
        int deposit = 400;
        String contactPerson = "Dan";
        Set<Tenant> tenants = new HashSet<>();
        tenants.add(t1);
        Rental rental = new Rental(startDate, endDate, priceAnnual, deposit, contactPerson, h1, tenants);
        RentalDTO rentalDTO = new RentalDTO(rental);
        String requestBody = GSON.toJson(rentalDTO);

        String expectedStartDate = startDate;
        String expectedEndDate = endDate;
        int expectedPriceAnnual = priceAnnual;
        int expectedDeposit = deposit;
        String expectedContactPerson = contactPerson;

        login("admin", "test");
        given()
                .contentType(ContentType.JSON)
                .header("x-access-token", securityToken)
                .and()
                .body(requestBody)
                .when()
                .post("/rentals/create")
                .then()
                .assertThat()
                .statusCode(200)
                .body("id", notNullValue())
                .body("startDate", equalTo(expectedStartDate))
                .body("endDate", equalTo(expectedEndDate))
                .body("priceAnnual", equalTo(expectedPriceAnnual))
                .body("deposit", equalTo(expectedDeposit))
                .body("contactPerson", equalTo(expectedContactPerson))
                .body("house", hasEntry("address", "address1"))
                .body("house", hasEntry("city", "city1"))
                .body("house", hasEntry("numberOfRooms", 1));
    }

    @Test
    public void testDelete() {
        System.out.println("Testing delete()");

        String expectedContactPerson = "Alice";
        login("admin", "test");
        given()
                .contentType(ContentType.JSON)
                .header("x-access-token", securityToken)
                .pathParam("id", r1.getId())
                .delete("/rentals/delete/{id}")
                .then()
                .statusCode(200)
                .body("contactPerson", equalTo(expectedContactPerson));
    }

    @Test
    void testGetAll() {
        System.out.println("Testing getAll()");
        List<RentalDTO> rentalDTOs;
        login("admin", "test");
        rentalDTOs = given()
                .contentType(ContentType.JSON)
                .header("x-access-token", securityToken)
                .when()
                .get("/rentals")
                .then()
                .extract()
                .body()
                .jsonPath()
                .getList("", RentalDTO.class);

        RentalDTO rentalDTO1 = new RentalDTO(r1);
        RentalDTO rentalDTO2 = new RentalDTO(r2);
        RentalDTO rentalDTO3 = new RentalDTO(r3);
        assertThat(rentalDTOs, containsInAnyOrder(rentalDTO1, rentalDTO2, rentalDTO3));
    }

    @Test
    public void testGetById() {
        System.out.println("Testing getById()");

        String expectedContactPerson = "Alice";
        login("admin", "test");
        given()
                .contentType(ContentType.JSON)
                .header("x-access-token", securityToken)
                .get("/rentals/id/{id}", r1.getId())
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("contactPerson", equalTo(expectedContactPerson));
    }

    @Test
    public void testGetByNotExistingId() {
        System.out.println("Testing getById() with not existing id");

        int expectedCode = 404;
        String expectedMessage = "Could not find a rental entity with id: 9999";
        login("admin", "test");
        given()
                .contentType(ContentType.JSON)
                .header("x-access-token", securityToken)
                .get("/rentals/id/{id}", 9999)
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND_404.getStatusCode())
                .body("code", equalTo(expectedCode))
                .body("message", equalTo(expectedMessage));
    }

}