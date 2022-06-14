package facades;

import dtos.RentalDTO;
import entities.House;
import entities.Rental;
import entities.Tenant;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.HashSet;
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

    public RentalDTO create(RentalDTO rentalDTO) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();

            String startDate = rentalDTO.getStartDate();
            String endDate = rentalDTO.getEndDate();
            int priceAnnual = rentalDTO.getPriceAnnual();
            int deposit = rentalDTO.getDeposit();
            String contactPerson = rentalDTO.getContactPerson();
            House house = em.find(House.class, rentalDTO.getHouse().getId());

            Set<Tenant> tenants = new HashSet<>();
            rentalDTO.getTenants().forEach(
                    tenant -> {
                        Tenant t = em.find(Tenant.class, tenant.getId());
                        tenants.add(t);
                    }
            );

            Rental rental = new Rental(startDate, endDate, priceAnnual, deposit, contactPerson, house, tenants);

            tenants.forEach(tenant -> {
                tenant.addRental(rental);
                em.merge(tenant);
            });

            house.addRental(rental);
            em.merge(house);

            em.persist(rental);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return rentalDTO;
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
