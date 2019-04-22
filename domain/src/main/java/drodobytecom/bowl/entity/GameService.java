package drodobytecom.bowl.entity;

/**
 * Simple entity service layer
 */
public interface GameService {

   /**
    * @param newGame creates a new game if true, loads previous saved game otherwise
    * @return the game instance
    */
   Game game(boolean newGame);

   /**
    * Saves the game instance
    *
    * @param game
    */
   void save(Game game);
}
