package facades;

import dtos.DemoDTO;
import entities.Demo;
import errorhandling.EntityNotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.Set;

public class DemoFacade {
    private static DemoFacade instance;
    private static EntityManagerFactory emf;

    public DemoFacade() {}

    public static DemoFacade getDemoFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new DemoFacade();
        }
        return instance;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public DemoDTO create(DemoDTO demoDTO) {
        Demo d = new Demo(demoDTO.getName());
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(d);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return demoDTO;
    }

    public DemoDTO update(DemoDTO demoDTO) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Demo demo = em.find(Demo.class, demoDTO.getId());
            demo.setName(demoDTO.getName());
            em.merge(demo);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return demoDTO;
    }

    public DemoDTO delete(long id) throws EntityNotFoundException{
        EntityManager em = getEntityManager();
        Demo d = em.find(Demo.class, id);
        if(d == null) {
            throw new EntityNotFoundException("Could not delete a demo entity with id: " + id);
        }
        try {
            em.getTransaction().begin();
            em.remove(d);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        DemoDTO demoDTO = new DemoDTO(d);
        return demoDTO;
    }

    public DemoDTO getById(long id) throws EntityNotFoundException{
        Demo d;
        EntityManager em = getEntityManager();
        try {
            d = em.find(Demo.class, id);
            if (d == null)
                throw new EntityNotFoundException("Could not find a demo entity with id: " + id);
        } finally {
            em.close();
        }
        DemoDTO demoDTO = new DemoDTO(d);
        return demoDTO;
    }

    public Set<DemoDTO> getAll() {
        Set<DemoDTO> demos;
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Demo> query = em.createQuery("SELECT d FROM Demo d", Demo.class);
            demos = DemoDTO.getDemoDTOs(query.getResultList());
        } finally {
            em.close();
        }
        return demos;
    }
}
