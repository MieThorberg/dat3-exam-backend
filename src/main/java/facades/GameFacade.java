package facades;

import entities.Game;
import entities.Player;

import javax.persistence.*;
import java.util.*;

public class GameFacade {

    private static EntityManagerFactory emf;
    private static GameFacade instance;

    public GameFacade() {
    }

    /**
     *
     * @param _emf
     * @return the instance of this facade.
     */
    public static GameFacade getGameFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new GameFacade();
        }
        return instance;
    }

    public Game createGame(Player host){
        Game game = new Game(host);

        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(host);
            em.persist(game);
            em.getTransaction().commit();
        } finally {
            em.close();
        }


        return game;
    }

}
