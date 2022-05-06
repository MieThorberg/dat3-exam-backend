package facades;

import entities.Game;
import entities.Player;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

public class PlayerFacade {
    private static EntityManagerFactory emf;
    private static PlayerFacade instance;

    public PlayerFacade() {

    }
    /**
     *
     * @param _emf
     * @return the instance of this facade.
     */
    public static PlayerFacade getPlayerFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PlayerFacade();
        }
        return instance;
    }

    public List<Player> getAll() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Player> query = em.createQuery("SELECT p FROM Player p", Player.class);
        List<Player> players = query.getResultList();
        return players;
    }

    public Player getPlayer(long id) {
        EntityManager em = emf.createEntityManager();
        Player player = em.find(Player.class, id);
        return player;
    }

    public int getCharacterName(String name) {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Integer> query = em.createQuery("SELECT p.characterName FROM Player p WHERE p.characterName = :name", Integer.class);
        return 0;
    }

    public boolean isAlice(long id) {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Boolean> query = em.createQuery("SELECT p.isAlive FROM Player p WHERE p.id = :id", Boolean.class);
        query.setParameter("id", id);
        boolean isAlive = query.getSingleResult();
        return isAlive;
    }

    public String getName(long id) {
        EntityManager em = emf.createEntityManager();
        TypedQuery<String> query = em.createQuery("SELECT p.user.userName FROM Player p WHERE p.id = :id", String.class);
        query.setParameter("id", id);
        String name = query.getSingleResult();
        return name;
    }


    //TODO: insert profileimage in userclass
//    public String getImageSource(long id) {
//        EntityManager em = emf.createEntityManager();
//        TypedQuery<String> query = em.createQuery("SELECT p FROM Player p WHERE p.id = :id", String.class);
//        query.setParameter("id", id);
//        String imageSource = query.getSingleResult();
//        return imageSource;
//    }


}
