package drodobytecom.bowl.usecase;

import drodobytecom.bowl.entity.Frame;
import drodobytecom.bowl.entity.Game;
import drodobytecom.bowl.entity.GameService;

public class StartGameCase extends AbstractCase {

   public StartGameCase(GameService service) {
      super(service);
   }

   public void start(Action action) {
      Game game = service.game(true);
      service.save(game);
      action.viewFrame(game.currentFrame());
   }

   public interface Action {
      void viewFrame(Frame frame);
   }
}
