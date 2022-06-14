package facades;

import dtos.RentalDTO;
import entities.House;
import entities.Rental;
import entities.Tenant;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RentalFacadeTest {
    private static EntityManagerFactory emf;
    private static RentalFacade facade;
    Rental r1, r2, r3;
    House h1, h2, h3;
    Tenant t1, t2, t3;

    @BeforeAll
    static void beforeAll() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = RentalFacade.getRentalFacade(emf);
    }

    @AfterAll
    static void afterAll() {

    }

    @BeforeEach
    void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Rental.deleteAllRows").executeUpdate();
            em.createNamedQuery("House.deleteAllRows").executeUpdate();
            em.createNamedQuery("Tenant.deleteAllRows").executeUpdate();
            h1 = new House("address1", "city1", 1);
            h1.setId(1);
            h2 = new House("address2", "city2", 2);
            h2.setId(2);
            h3 = new House("address3", "city3", 3);
            h3.setId(3);
            em.persist(h1);
            em.persist(h2);
            em.persist(h3);
            t1 = new Tenant("name1", "phone1", "job1");
            t2 = new Tenant("name2", "phone2", "job2");
            t3 = new Tenant("name3", "phone3", "job3");
            em.persist(t1);
            em.persist(t2);
            em.persist(t3);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        EntityManager em1 = emf.createEntityManager();
        try {
            em1.getTransaction().begin();
            Set<Tenant> tenants = new HashSet<>();
            tenants.add(t1);
            r1 = new Rental("01-01-2001", "01-01-2002", 100, 100, "Alice", h1, tenants);
            r2 = new Rental("02-02-2002", "02-02-2003", 200, 200, "Bob", h2, tenants);
            r3 = new Rental("03-03-2003", "03-03-2004", 300, 300, "Charlie", h3, tenants);
            em1.persist(r1);
            em1.persist(r2);
            em1.persist(r3);
            em1.getTransaction().commit();
        } finally {
            em1.close();
        }
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void create() {
        System.out.println("Testing create()");

        Set<Tenant> tenants = new HashSet<>();
        tenants.add(t1);
        Rental rental = new Rental("04-04-2004", "04-04-2005", 400, 400, "Dan", h1, tenants);

        RentalDTO rentalDTO = new RentalDTO(rental);

        RentalDTO expected = rentalDTO;
        RentalDTO actual = facade.create(rentalDTO);
        assertEquals(expected, actual);

        int expectedSize = 4;
        int actualSize = facade.getAll().size();
        assertEquals(expectedSize, actualSize);
    }

    @Test
    void testGetAll() {
        System.out.println("Testing getAll()");

        Set<RentalDTO> rentalDTOs = new HashSet<>();
        rentalDTOs.add(new RentalDTO(r1));
        rentalDTOs.add(new RentalDTO(r2));
        rentalDTOs.add(new RentalDTO(r3));
        Set<RentalDTO> expected = rentalDTOs;
        Set<RentalDTO> actual = facade.getAll();

        assertEquals(expected.size(), actual.size());
        assertTrue(actual.containsAll(expected));
    }
}