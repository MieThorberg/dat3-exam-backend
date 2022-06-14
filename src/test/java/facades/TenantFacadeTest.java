package facades;

import dtos.HouseDTO;
import dtos.TenantDTO;
import entities.House;
import entities.Role;
import entities.Tenant;
import entities.User;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.jupiter.api.Assertions.*;

class TenantFacadeTest {
    private static EntityManagerFactory emf;
    private static TenantFacade facade;
    Tenant t1, t2, t3;
    User user, user1, user2, admin, both;

    @BeforeAll
    static void beforeAll() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = TenantFacade.getTenantFacade(emf);
    }

    @AfterAll
    static void afterAll() {

    }

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
            user = new User("user", "test");
            user.addRole(userRole);
            user1 = new User("user1", "test");
            user1.addRole(userRole);
            user2 = new User("user2", "test");
            user2.addRole(userRole);
            admin = new User("admin", "test");
            admin.addRole(adminRole);
            both = new User("user_admin", "test");
            both.addRole(userRole);
            both.addRole(adminRole);
            t1 = new Tenant("user", "11111111", "job1");
            t2 = new Tenant("user1", "22222222", "job2");
            t3 = new Tenant("user2", "33333333", "job1");
            em.persist(userRole);
            em.persist(adminRole);
            em.persist(user);
            em.persist(user1);
            em.persist(user2);
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
    void testGetAll() {
        System.out.println("Testing getAll()");

        Set<TenantDTO> tenantDTOs = new HashSet<>();
        TenantDTO tenantDTO = new TenantDTO(t1);
        TenantDTO tenantDTO1 = new TenantDTO(t2);
        TenantDTO tenantDTO2 = new TenantDTO(t3);
        tenantDTOs.add(tenantDTO);
        tenantDTOs.add(tenantDTO1);
        tenantDTOs.add(tenantDTO2);
        Set<TenantDTO> expected = tenantDTOs;
        Set<TenantDTO> actual = facade.getAll();
        assertEquals(expected.size(), actual.size());
    }
}