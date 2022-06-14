package facades;

import dtos.HouseDTO;
import dtos.RentalDTO;
import entities.House;
import entities.Rental;
import errorhandling.EntityNotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.Set;

public class HouseFacade {
    private static HouseFacade instance;
    private static EntityManagerFactory emf;

    public HouseFacade() {}

    public static HouseFacade getHouseFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new HouseFacade();
        }
        return instance;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }


    public HouseDTO create(HouseDTO houseDTO) {
        House h = new House(houseDTO.getAddress(), houseDTO.getCity(), houseDTO.getNumberOfRooms());
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(h);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return houseDTO;
    }

    public Set<HouseDTO> getAll() {
        Set<HouseDTO> houses;
        EntityManager em = getEntityManager();
        try {
            TypedQuery<House> query = em.createQuery("SELECT h FROM House h", House.class);
            houses = HouseDTO.getHouseDTOs(query.getResultList());
        } finally {
            em.close();
        }
        return houses;
    }

    public HouseDTO getById(long id) {
        House h;
        EntityManager em = getEntityManager();
        try {
            h = em.find(House.class, id);
            if (h == null)
                throw new EntityNotFoundException("Could not find a house entity with id: " + id);
        } finally {
            em.close();
        }
        HouseDTO houseDTO = new HouseDTO(h);
        return houseDTO;
    }

}
