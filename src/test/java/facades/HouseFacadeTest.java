package facades;

import dtos.HouseDTO;
import dtos.RentalDTO;
import entities.House;
import entities.Rental;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class HouseFacadeTest {

    private static EntityManagerFactory emf;
    private static HouseFacade facade;
    House h1, h2, h3;

    @BeforeAll
    static void beforeAll() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = HouseFacade.getHouseFacade(emf);
    }

    @AfterAll
    static void afterAll() {

    }

    @BeforeEach
    void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("House.deleteAllRows").executeUpdate();
            h1 = new House("address1", "city1", 1);
            h2 = new House("address2", "city2", 2);
            h3 = new House("address3", "city3", 3);
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
    void testGetAll() {
        System.out.println("Testing getAll()");

        Set<HouseDTO> houseDTOs = new HashSet<>();
        houseDTOs.add(new HouseDTO(h1));
        houseDTOs.add(new HouseDTO(h2));
        houseDTOs.add(new HouseDTO(h3));
        Set<HouseDTO> expected = houseDTOs;
        Set<HouseDTO> actual = facade.getAll();

        assertEquals(expected.size(), actual.size());
        assertTrue(actual.containsAll(expected));
    }
}