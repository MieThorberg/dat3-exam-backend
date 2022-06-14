package facades;

import dtos.HouseDTO;
import dtos.RentalDTO;
import entities.House;
import entities.Rental;

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

}
