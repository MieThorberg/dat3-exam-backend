package facades;

import controller.VoteController;
import dtos.PlayerDTO;
import entities.*;

import javax.persistence.*;
import java.util.*;

import controller.GameController;

public class GameFacade {

    private static EntityManagerFactory emf;
    private static GameFacade instance;
    private GameController gc;
    private VoteController vc;

    public GameFacade() {
    }

    /**
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

    public List<Game> getAllGames() {
        EntityManager em = emf.createEntityManager();

        TypedQuery<Game> query = em.createQuery("SELECT g FROM Game g", Game.class);

        return query.getResultList();
    }

    public Game getGameById(long id) {
        EntityManager em = emf.createEntityManager();
        return em.find(Game.class, id);
    }

    public Game createGame(User host) {
        EntityManager em = emf.createEntityManager();
        gc = new GameController();
        Game game = gc.createGame(host);
        NightRound nightRound = new NightRound(game);

        try {
            em.getTransaction().begin();
            em.persist(nightRound);
            em.persist(game);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return game;
    }

    public NightRound createNightRound(long gameId) {
        EntityManager em = emf.createEntityManager();

        Game game = em.find(Game.class, gameId);

        NightRound nightRound = new NightRound(game);

        try {
            em.getTransaction().begin();
            em.merge(game);
            em.getTransaction().commit();
        } finally {
            em.close();
        }

        return nightRound;
    }

    public DayRound createDayRound(long gameId) {
        EntityManager em = emf.createEntityManager();

        Game game = em.find(Game.class, gameId);

        DayRound dayRound = new DayRound(game);

        try {
            em.getTransaction().begin();
            em.merge(game);
            em.getTransaction().commit();
        } finally {
            em.close();
        }

        return dayRound;
    }

    public List<Player> createPlayers(long gameId, List<Player> players) {
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

    public Player createPlayer(long gameId, Player player) {
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        Game game = em.find(Game.class, gameId);

        player.setGame(game);
        em.persist(player);

        em.getTransaction().commit();

        return player;
    }

    public List<Player> getAllPlayersByGameId(long gameId) {
        EntityManager em = emf.createEntityManager();

        TypedQuery<Player> query = em.createQuery("SELECT p FROM Player p WHERE p.game.id = :gameId", Player.class);
        query.setParameter("gameId", gameId);

        return query.getResultList();
    }


    public List<Player> getAllVictims(long id) {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Player> query = em.createQuery("SELECT g.victims FROM Game g WHERE g.id = :id", Player.class);
        query.setParameter("id", id);
        return query.getResultList();
    }

    public Player getLatestVictim(long id) {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Player> query = em.createQuery("SELECT g.latestVictim FROM Game g WHERE g.id = :id", Player.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    public List<Player> getAllAlivePlayers(long id) {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Player> query = em.createQuery("SELECT g.players FROM Game g WHERE g.id = :id", Player.class);
        query.setParameter("id", id);
        return query.getResultList();
    }

    public List<Player> getWerewolves(long id) {
        EntityManager em = emf.createEntityManager();

        Game game = em.find(Game.class, id);

        return game.getWerewolves();
    }

    public int getDay(long gameId) {
        EntityManager em = emf.createEntityManager();

        Game game = em.find(Game.class, gameId);

        return game.getDays();
    }

    public int addDays(long gameId) {
        EntityManager em = emf.createEntityManager();

        Game game = em.find(Game.class, gameId);
        game.addDay();

        em.getTransaction().begin();
        em.merge(game);
        em.getTransaction().commit();


        return game.getDays();
    }

    public List<NightRound> getNightRounds(long gameId) {
        EntityManager em = emf.createEntityManager();

        TypedQuery<NightRound> query = em.createQuery("SELECT n FROM NightRound n WHERE n.game.id = :id", NightRound.class);
        query.setParameter("id", gameId);

        return query.getResultList();
    }

    public List<DayRound> getDayRounds(long gameId) {
        EntityManager em = emf.createEntityManager();

        TypedQuery<DayRound> query = em.createQuery("SELECT d FROM DayRound d WHERE d.game.id = :id", DayRound.class);
        query.setParameter("id", gameId);

        return query.getResultList();
    }

    public DayRound getDayRoundsByDay(long gameId, int day) {
        EntityManager em = emf.createEntityManager();

        TypedQuery<DayRound> query = em.createQuery("SELECT d FROM DayRound d WHERE d.game.id = :id AND d.day = :day", DayRound.class);
        query.setParameter("id", gameId);
        query.setParameter("day", day);

        return query.getSingleResult();
    }

    public NightRound getNightRoundsByDay(long gameId, int day) {
        EntityManager em = emf.createEntityManager();

        TypedQuery<NightRound> query = em.createQuery("SELECT n FROM NightRound n WHERE n.game.id = :id AND n.day = :day", NightRound.class);
        query.setParameter("id", gameId);
        query.setParameter("day", day);

        return query.getSingleResult();
    }

    public NightRound getNightRoundsByID(long gameId, long roundId) {
        EntityManager em = emf.createEntityManager();

        TypedQuery<NightRound> query = em.createQuery("SELECT n FROM NightRound n WHERE n.game.id = :id AND n.id = :roundId", NightRound.class);
        query.setParameter("id", gameId);
        query.setParameter("roundId", roundId);

        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public DayRound getDayRoundsByID(long gameId, long roundId) {
        EntityManager em = emf.createEntityManager();

        TypedQuery<DayRound> query = em.createQuery("SELECT d FROM DayRound d WHERE d.game.id = :id AND d.id = :roundId", DayRound.class);
        query.setParameter("id", gameId);
        query.setParameter("roundId", roundId);

        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public NightRound nightRoundResult(long gameId){
        EntityManager em = emf.createEntityManager();

        NightRound nightRound = getCurrentNightRound(gameId);
        nightRound.start();

        em.getTransaction().begin();
        em.merge(nightRound);
        em.merge(nightRound.getGame());
        em.getTransaction().commit();

        return nightRound;
    }

    public DayRound dayRoundResult(long gameId){
        EntityManager em = emf.createEntityManager();

        DayRound dayRound = getCurrentDayRound(gameId);
        dayRound.start();

        em.getTransaction().begin();
        em.merge(dayRound);
        em.merge(dayRound.getGame());
        em.getTransaction().commit();

        return dayRound;
    }

    public Player setPlayerVote(long playerId, PlayerDTO playerDTO) {
        EntityManager em = emf.createEntityManager();

        Player player = em.find(Player.class, playerId);
        Player vote = em.find(Player.class, playerDTO.getId());
        player.setLatestVote(vote);

        try {
            em.getTransaction().begin();
            em.merge(player);
            em.getTransaction().commit();
        } finally {
            em.close();
        }

        return player;
    }

    public Player getVoteResult(long gameId) {
        EntityManager em = emf.createEntityManager();
        vc = new VoteController();

        Game game = em.find(Game.class, gameId);

        return vc.startVotingCalculator(game);
    }

    public void cleanVotes(long id) {
        EntityManager em = emf.createEntityManager();
        Game game = em.find(Game.class, id);

        for (Player player : game.getPlayers()) {
            player.setLatestVote(null);
        }

        em.getTransaction().begin();
        em.merge(game);
        em.getTransaction().commit();
    }

    public Player killPlayer(long gameId, PlayerDTO playerDTO) {
        EntityManager em = emf.createEntityManager();
        Game game = em.find(Game.class, gameId);
        Player playerToKill = em.find(Player.class, playerDTO.getId());

        gc = new GameController(game);
        gc.kill(playerToKill);

        em.getTransaction().begin();
        em.merge(game);
        em.getTransaction().commit();

        return playerToKill;
    }

    public List<Player> assignCharacters(long gameId, int amountOfWerewolves) {
        EntityManager em = emf.createEntityManager();

        Game game = em.find(Game.class, gameId);
        gc = new GameController(game);

        gc.characterAssigning(amountOfWerewolves);

        em.getTransaction().begin();
        em.merge(game);
        em.getTransaction().commit();

        return game.getPlayers();
    }

    public Boolean hasEnded(long gameId) {
        EntityManager em = emf.createEntityManager();

        Game game = em.find(Game.class, gameId);
        gc = new GameController(game);

        return gc.hasEnded();
    }

    public NightRound getCurrentNightRound(long gameId) {
        EntityManager em = emf.createEntityManager();

        try {
        TypedQuery<Integer> query = em.createQuery("SELECT MAX(n.id) FROM NightRound n WHERE n.game.id = :id", Integer.class);
        query.setParameter("id", gameId);

            return em.find(NightRound.class, query.getSingleResult());
        } catch (NoResultException | IllegalArgumentException e) {
            return null;
        }
    }

    public DayRound getCurrentDayRound(long gameId) {
        EntityManager em = emf.createEntityManager();

        try {
            TypedQuery<Integer> query = em.createQuery("SELECT MAX(d.id) FROM DayRound d WHERE d.game.id = :id", Integer.class);
            query.setParameter("id", gameId);

            return em.find(DayRound.class, query.getSingleResult());
        } catch ( NoResultException | IllegalArgumentException e) {
            return null;
        }
    }

    public List<Rules> getAllRules() {
        EntityManager em = emf.createEntityManager();

        TypedQuery<Rules> query = em.createQuery("SELECT r FROM Rules r", Rules.class);

        return query.getResultList();
    }
}
