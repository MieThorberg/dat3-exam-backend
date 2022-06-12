package facades;

import dtos.DemoDTO;
import entities.Demo;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class DemoFacadeTest {

    private static EntityManagerFactory emf;
    private static DemoFacade facade;
    Demo d1, d2, d3;

    @BeforeAll
    static void beforeAll() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = DemoFacade.getDemoFacade(emf);
    }

    @AfterAll
    static void afterAll() {

    }

    @BeforeEach
    void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Demo.deleteAllRows").executeUpdate();
            d1 = new Demo("name1");
            d2 = new Demo("name2");
            d3 = new Demo("name3");
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
    void testCreate() {
        System.out.println("Testing create()");

        Demo demo = new Demo("name4");
        DemoDTO demoDTO = new DemoDTO(demo);

        DemoDTO expected = demoDTO;
        DemoDTO actual = facade.create(demoDTO);
        assertEquals(expected, actual);

        int expectedSize = 4;
        int actualSize = facade.getAll().size();
        assertEquals(expectedSize, actualSize);
    }

    @Test
    void testUpdate() {
        System.out.println("Testing update()");

        String newName = "newName";
        DemoDTO demoDTO = new DemoDTO(d1);
        demoDTO.setId(d1.getId());
        demoDTO.setName(newName);

        DemoDTO expected = demoDTO;
        DemoDTO actual = facade.update(demoDTO);
        assertEquals(expected, actual);

        String expectedName = newName;
        String actualName = facade.getById(d1.getId()).getName();
        assertEquals(expectedName, actualName);
    }

    @Test
    void testDelete() {
        System.out.println("Testing delete()");

        DemoDTO demoDTO = new DemoDTO(d1);

        DemoDTO expected = demoDTO;
        DemoDTO actual = facade.delete(d1.getId());
        assertEquals(expected, actual);

        int expectedSize = 2;
        int actualSize = facade.getAll().size();
        assertEquals(expectedSize, actualSize);
    }

    @Test
    void testGetById() {
        System.out.println("Testing getById()");

        long id1 = d1.getId();
        DemoDTO expected1 = new DemoDTO(d1);
        DemoDTO actual1 = facade.getById(id1);
        assertEquals(expected1, actual1);

        long id2 = d2.getId();
        DemoDTO expected2 = new DemoDTO(d2);
        DemoDTO actual2 = facade.getById(id2);
        assertEquals(expected2, actual2);

        long id3 = d3.getId();
        DemoDTO expected3 = new DemoDTO(d3);
        DemoDTO actual3 = facade.getById(id3);
        assertEquals(expected3, actual3);
    }

    @Test
    void testGetAll() {
        System.out.println("Testing getAll()");

        Set<DemoDTO> demoDTOs = new HashSet<>();
        demoDTOs.add(new DemoDTO(d1));
        demoDTOs.add(new DemoDTO(d2));
        demoDTOs.add(new DemoDTO(d3));
        Set<DemoDTO> expected = demoDTOs;
        Set<DemoDTO> actual = facade.getAll();

        assertEquals(expected.size(), actual.size());
        assertTrue(actual.containsAll(expected));
    }
}