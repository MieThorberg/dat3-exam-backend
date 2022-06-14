package rest;

import com.google.gson.Gson;
import dtos.HouseDTO;
import dtos.RentalDTO;
import dtos.TenantDTO;
import entities.*;
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
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

class TenantResourceTest {
    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
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
            em.createNamedQuery("Tenant.deleteAllRows").executeUpdate();
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
            t1 = new Tenant("name1", "phone1", "job1");
            t2 = new Tenant("name1", "phone2", "job2");
            t3 = new Tenant("name1", "phone3", "job3");

            em.persist(userRole);
            em.persist(adminRole);
            em.persist(user);
            em.persist(admin);
            em.persist(both);

            em.persist(t1);
            em.persist(t2);
            em.persist(t3);
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
                .when().get("/tenants")
                .then().statusCode(200);
    }

    @Test
    void testLogRequest() {
        System.out.println("Testing logging request details");
        login("admin", "test");
        given().log().all()
                .header("x-access-token", securityToken)
                .when().get("/tenants")
                .then().statusCode(200);
    }

    @Test
    void testLogResponse() {
        System.out.println("Testing logging response details");
        login("admin", "test");
        given()
                .header("x-access-token", securityToken)
                .when().get("/tenants")
                .then().log().body().statusCode(200);
    }

    @Test
    public void testCreate() {
//        System.out.println("Testing create()");
//
//        String name4 = "name4";
//        String phone4 = "phone4";
//        String job4 = "job4";
//
//
//        Tenant tenant = new Tenant(name4, phone4, job4);
//        TenantDTO tenantDTO = new TenantDTO(tenant);
//        String requestBody = GSON.toJson(tenantDTO);
//
//        String expectedName = name4;
//        String expectedPhone = phone4;
//        String expectedJob = job4;
//
//        login("admin", "test");
//        given()
//                .contentType(ContentType.JSON)
//                .header("x-access-token", securityToken)
//                .and()
//                .body(requestBody)
//                .when()
//                .post("/tenants/create")
//                .then()
//                .assertThat()
//                .statusCode(200)
//                .body("name", equalTo(expectedName))
//                .body("phone", equalTo(expectedPhone))
//                .body("job", equalTo(expectedJob));
    }

    @Test
    void testGetAll() {
        System.out.println("Testing getAll()");
        List<TenantDTO> tenantDTOs;
        login("admin", "test");
        tenantDTOs = given()
                .contentType(ContentType.JSON)
                .header("x-access-token", securityToken)
                .when()
                .get("/tenants")
                .then()
                .extract()
                .body()
                .jsonPath()
                .getList("", TenantDTO.class);

        TenantDTO tenantDTO1 = new TenantDTO(t1);
        TenantDTO tenantDTO2 = new TenantDTO(t2);
        TenantDTO tenantDTO3 = new TenantDTO(t3);

        assertThat(tenantDTOs, containsInAnyOrder(tenantDTO1, tenantDTO2, tenantDTO3));
    }
}