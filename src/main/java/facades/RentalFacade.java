package facades;

import dtos.RentalDTO;
import dtos.TenantDTO;
import entities.House;
import entities.Rental;
import entities.Tenant;
import errorhandling.EntityNotFoundException;

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

    public RentalDTO update(RentalDTO rentalDTO) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();

            //get all tenants related to the rental
            Set<Tenant> tenants = new HashSet<>();
            rentalDTO.getTenants().forEach(
                    tenant -> {
                        tenants.add((em.find(Tenant.class, tenant.getId())));
                    }
            );

            //get house related to the rental
            House house = em.find(House.class, rentalDTO.getHouse().getId());

            //rental before update
            Rental currentRental = em.find(Rental.class, rentalDTO.getId());

            //merging tenants to update their relation to rental
            currentRental.getTenants().forEach(tenant -> {
                tenant.removeRental(currentRental);
                em.merge(tenant);
            });

            currentRental.getTenants().clear();

            tenants.forEach(tenant -> {
                currentRental.addTenant(tenant);
                em.merge(tenant);
            });

            //merging house to update their relation to boat
            if(!currentRental.getHouse().equals(house)) {
                currentRental.getHouse().removeRental(currentRental);
                em.merge(currentRental.getHouse());
                currentRental.setHouse(house);
            }

            //saving boat
            currentRental.setStartDate(rentalDTO.getStartDate());
            currentRental.setEndDate(rentalDTO.getEndDate());
            currentRental.setPriceAnnual(rentalDTO.getPriceAnnual());
            currentRental.setDeposit(rentalDTO.getDeposit());
            currentRental.setContactPerson(rentalDTO.getContactPerson());
            em.merge(currentRental);

            em.getTransaction().commit();

        } finally {
            em.close();
        }
        return rentalDTO;
    }

    public RentalDTO delete(long id) {
        RentalDTO rentalDTO;
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();

            Rental rental = em.find(Rental.class, id);
            if(rental == null) {
                throw new EntityNotFoundException("Could not find a rental entity with id: " + id);
            }
            rentalDTO = new RentalDTO(rental);

            rental.getTenants().forEach(tenant -> {
                tenant.removeRental(rental);
                em.merge(tenant);
            });

            rental.getTenants().clear();

            rental.getHouse().removeRental(rental);

            em.remove(rental);

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

    public RentalDTO getById(long id) {
        Rental r;
        EntityManager em = getEntityManager();
        try {
            r = em.find(Rental.class, id);
            if (r == null)
                throw new EntityNotFoundException("Could not find a rental entity with id: " + id);
        } finally {
            em.close();
        }
        RentalDTO rentalDTO = new RentalDTO(r);
        return rentalDTO;
    }

    public Set<TenantDTO> getTenantsFromHouseById(long id) {
        Set<TenantDTO> tenants;
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Tenant> query = em.createQuery("SELECT r.tenants FROM Rental r WHERE r.house.id =:id", Tenant.class);
            query.setParameter("id", id);
            tenants = TenantDTO.getTenantDTOs(query.getResultList());
        } finally {
            em.close();
        }
        return tenants;
    }

    public Set<RentalDTO> getRentalByTenantName(String name) {
        Set<RentalDTO> rentals;
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Rental> query = em.createQuery("SELECT t.rentals FROM Tenant t WHERE t.name =:name", Rental.class);
            query.setParameter("name", name);
            rentals = RentalDTO.getRentalDTOs(query.getResultList());
        } finally {
            em.close();
        }
        return rentals;
    }
}
