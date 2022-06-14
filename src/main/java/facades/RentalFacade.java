package facades;

import dtos.RentalDTO;
import entities.Rental;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.Set;

public class RentalFacade {
    private static RentalFacade instance;
    private static EntityManagerFactory emf;

    public RentalFacade() {}

    public static RentalFacade getRentalFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new RentalFacade();
        }
        return instance;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public Set<RentalDTO> getAll() {
        Set<RentalDTO> rentals;
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Rental> query = em.createQuery("SELECT r FROM Rental r", Rental.class);
            rentals = RentalDTO.getRentalDTOs(query.getResultList());
        } finally {
            em.close();
        }
        return rentals;
    }
}
