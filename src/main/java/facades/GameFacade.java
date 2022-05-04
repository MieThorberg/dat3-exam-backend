package facades;

import entities.Game;
import entities.Player;
import entities.User;

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

    public Game createGame(User host){
        Game game = new Game(host);
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(game);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return game;
    }

    public Game getGameById(long id) {
        EntityManager em = emf.createEntityManager();
        Game game = em.find(Game.class, id);
        return game;
    }


    public void startGame() {

    }

}
