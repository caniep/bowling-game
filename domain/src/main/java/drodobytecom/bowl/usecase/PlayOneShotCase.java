package drodobytecom.bowl.usecase;

import drodobytecom.bowl.entity.Game;
import drodobytecom.bowl.entity.GameService;

public class PlayOneShotCase extends AbstractCase {

   public PlayOneShotCase(GameService service) {
      super(service);
   }

   public void shot(int pinsDown, Action action) {
      Game game = service.game(false);
      Game.Frame frame = game.shot(pinsDown);
      service.save(game);

      if (game.isDone())
         action.viewScore();
      else
         action.viewPlay(frame.number(), frame.attempts().size() + 1, frame.pinsLeft());
   }

   public interface Action {
      void viewPlay(int frame, int attempt, int pinsLeft);

      void viewScore();
   }
}
