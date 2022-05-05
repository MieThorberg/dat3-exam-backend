package facades;

import dtos.PlayerDTO;
import entities.Game;
import entities.Player;
import entities.User;

import javax.persistence.*;
import java.util.*;
import controller.GameController;

public class GameFacade {

    private static EntityManagerFactory emf;
    private static GameFacade instance;
    private GameController gc;

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

    public List<Game> getAllGames(){
        EntityManager em = emf.createEntityManager();

        TypedQuery<Game> query = em.createQuery("SELECT g FROM Game g", Game.class);

        return query.getResultList();
    }

    public Game getGameById(long id) {
        EntityManager em = emf.createEntityManager();
        return em.find(Game.class, id);
    }

    public Game createGame(User host){
        EntityManager em = emf.createEntityManager();

        gc = new GameController();
        Game game = gc.createGame(host);

        try {
            em.getTransaction().begin();
            em.persist(game);
            em.getTransaction().commit();
        } finally {
            em.close();
        }

        return game;
    }

    public Game startGame(Game game){
        EntityManager em = emf.createEntityManager();

        gc = new GameController(game);
//        Game startGame = gc.startGame();

        try {
            em.getTransaction().begin();
            em.persist(game);
            em.getTransaction().commit();
        } finally {
            em.close();
        }

        return game;
    }

    public List<Player> createPlayers(long gameId,List<Player> players){
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        Game game = em.find(Game.class, gameId);

        for (Player player : players) {
            player.setGame(game);
            em.persist(player);
        }
        em.getTransaction().commit();

        return players;
    }

    public List<Player> getAllPlayersByGameId(long gameId){
        EntityManager em = emf.createEntityManager();

        TypedQuery<Player> query = em.createQuery("SELECT p FROM Player p WHERE p.game.id = :gameId", Player.class);
        query.setParameter("gameId",gameId);

        return query.getResultList();
    }


    public List<Player> getAllVictims(long id) {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Player> query = em.createQuery("SELECT g.victims FROM Game g WHERE g.id = :id", Player.class);
        query.setParameter("id", id);
        List<Player> victims = query.getResultList();
        return victims;
    }

    public Player getLatestVictim(long id) {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Player> query = em.createQuery("SELECT g.latestVictim FROM Game g WHERE g.id = :id", Player.class);
        query.setParameter("id", id);
        Player player = query.getSingleResult();
        return player;
    }
}
