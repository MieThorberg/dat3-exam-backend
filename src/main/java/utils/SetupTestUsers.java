package utils;


import java.util.List;
import entities.*;
import facades.GameFacade;
import facades.UserFacade;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.validation.constraints.Max;
import java.util.ArrayList;

public class SetupTestUsers {

  public static void main(String[] args) {

    EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
    EntityManager em = emf.createEntityManager();
    
    // IMPORTAAAAAAAAAANT!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    // This breaks one of the MOST fundamental security rules in that it ships with default users and passwords
    // CHANGE the three passwords below, before you uncomment and execute the code below
    // Also, either delete this file, when users are created or rename and add to .gitignore
    // Whatever you do DO NOT COMMIT and PUSH with the real passwords

    User user = new User("user", "test123");
    User admin = new User("admin", "test123");
    User both = new User("user_admin", "test123");
    // Player player = new Player(user);
    Player player1 = new Player(admin);
    Player player2 = new Player(both);


    Game game = new Game(admin);
    player1.setGame(game);
    player2.setGame(game);

    Rule rule1 = new Rule("Assemble at least 7 players. An odd number of players is best, but it isn’t mandatory for a game");
    Rule rule2 = new Rule("The first phase of a game of Werewolf is the night round");
    Rule rule3 = new Rule("The werewolves chooses who they want to kill during the night/discussion round");
    Rule rule5 = new Rule("The discussion rounds lasts * min");
    Rule rule4 = new Rule("When the werewolves make a decision and agree on a victim they vote");
    Rule rule6 = new Rule("the voting rounds last * seconds or until all have voted");
    Rule rule7 = new Rule("If the villagers kill all the werewolves they win the game");
    Rule rule8 = new Rule("If the werewolves outnumber the villagers, they win the game");
    List<Rule> rules = new ArrayList<>();
    rules.add(rule1);
    rules.add(rule2);
    rules.add(rule3);
    rules.add(rule4);
    rules.add(rule5);
    rules.add(rule6);
    rules.add(rule7);
    rules.add(rule8);

    NightRound round = new NightRound(game);
    DayRound round1 = new DayRound(game);

    game.getNightRounds().add(round);
    game.getDayRounds().add(round1);

    if(admin.getUserPass().equals("test")||user.getUserPass().equals("test")||both.getUserPass().equals("test"))
    {
      throw new UnsupportedOperationException("You have not changed the passwords");
    }

    em.getTransaction().begin();
    Role userRole = new Role("user");
    Role adminRole = new Role("admin");
    admin.addRole(adminRole);
    both.addRole(userRole);
    both.addRole(adminRole);
    em.persist(userRole);
    em.persist(adminRole);
//    em.persist(user);
    em.persist(admin);
    em.persist(both);

    em.persist(player1);
    em.persist(player2);
    em.persist(game);
//    user.addRole(userRole);
    em.persist(round);
    em.persist(round1);
    em.getTransaction().commit();
    UserFacade.getUserFacade(emf).registerNewUser(user);
    GameFacade.getGameFacade(emf).createRules(rules);
    System.out.println("PW: " + user.getUserPass());
    System.out.println("Testing user with OK password: " + user.verifyPassword("test123"));
    System.out.println("Testing user with wrong password: " + user.verifyPassword("test1"));
    System.out.println("Created TEST Users");
   
  }

}
