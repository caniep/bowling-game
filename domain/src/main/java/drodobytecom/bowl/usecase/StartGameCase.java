package drodobytecom.bowl.usecase;

import java.util.List;

import drodobytecom.bowl.entity.GameService;
import drodobytecom.bowl.entity.Stats;
import drodobytecom.bowl.entity.DataService;

public class StartGameCase extends AbstractCase {

   public StartGameCase(GameService gameService, DataService dataService) {
      super(gameService, dataService);
   }

   public void start(Action action) {
      List<Integer> shots = dataService.loadShots();
      Stats stats = gameService.play(shots);
      dataService.saveShots(shots);

      action.viewPlay(stats.nextFrame(), stats.nextAttempt(), stats.nextPinsLeft());
   }

   public interface Action {
      void viewPlay(int frame, int attempt, int pinsLeft);
   }
}
