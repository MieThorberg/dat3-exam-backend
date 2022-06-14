package facades;

import dtos.RentalDTO;
import entities.Rental;
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
            r1 = new Rental("01-01-2001", "01-01-2002", 100, 100, "Alice");
            r2 = new Rental("02-02-2002", "02-02-2003", 200, 200, "Bob");
            r3 = new Rental("03-03-2003", "03-03-2004", 300, 300, "Charlie");
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
    void getAll() {
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