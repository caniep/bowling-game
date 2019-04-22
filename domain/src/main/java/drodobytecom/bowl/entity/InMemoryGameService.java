package drodobytecom.bowl.entity;

public class InMemoryGameService implements GameService {

   private Game game;

   @Override
   public Game game(boolean newGame) {
      return newGame ? new Game() : game;
   }

   @Override
   public void save(Game game) {
      this.game = game;
   }
}
