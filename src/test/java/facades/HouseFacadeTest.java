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
            em.createNamedQuery("Rental.deleteAllRows").executeUpdate();
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
    void testCreate() {
        System.out.println("Testing create()");

        House house = new House("address1", "city", 1);
        HouseDTO houseDTO = new HouseDTO(house);

        HouseDTO expected = houseDTO;
        HouseDTO actual = facade.create(houseDTO);
        assertEquals(expected, actual);

        int expectedSize = 4;
        int actualSize = facade.getAll().size();
        assertEquals(expectedSize, actualSize);
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

    @Test
    void testGetById() {
        System.out.println("Testing getById()");

        long id1 = h1.getId();
        HouseDTO expected1 = new HouseDTO(h1);
        HouseDTO actual1 = facade.getById(id1);
        assertEquals(expected1, actual1);

        long id2 = h2.getId();
        HouseDTO expected2 = new HouseDTO(h2);
        HouseDTO actual2 = facade.getById(id2);
        assertEquals(expected2, actual2);

        long id3 = h3.getId();
        HouseDTO expected3 = new HouseDTO(h3);
        HouseDTO actual3 = facade.getById(id3);
        assertEquals(expected3, actual3);
    }
}