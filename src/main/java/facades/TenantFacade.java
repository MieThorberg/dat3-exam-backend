package facades;

import dtos.RentalDTO;
import dtos.TenantDTO;
import entities.Rental;
import entities.Tenant;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.Set;

public class TenantFacade {
    private static TenantFacade instance;
    private static EntityManagerFactory emf;

    public TenantFacade() {}

    public static TenantFacade getTenantFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new TenantFacade();
        }
        return instance;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public Set<TenantDTO> getAll() {
        Set<TenantDTO> tenants;
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Tenant> query = em.createQuery("SELECT t FROM Tenant t", Tenant.class);
            tenants = TenantDTO.getTenantDTOs(query.getResultList());
        } finally {
            em.close();
        }
        return tenants;
    }
}
