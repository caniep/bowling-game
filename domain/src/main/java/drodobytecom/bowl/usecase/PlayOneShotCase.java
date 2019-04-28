package drodobytecom.bowl.usecase;

import java.util.List;

import drodobytecom.bowl.entity.GameService;
import drodobytecom.bowl.entity.Score;
import drodobytecom.bowl.entity.Stats;
import drodobytecom.bowl.entity.DataService;

public class PlayOneShotCase extends AbstractCase {

   public PlayOneShotCase(GameService gameService, DataService dataService) {
      super(gameService, dataService);
   }

   public void shot(int hits, Action action) {
      Stats stats = playPreviousShotsWithNewShot(hits);
      if (stats.isGameDone())
         action.viewScore(stats.score());
      else
         action.viewPlay(stats.nextFrame(), stats.nextAttempt(), stats.nextPinsLeft());
   }

   private Stats playPreviousShotsWithNewShot(int newShot) {
      List<Integer> shots = dataService.loadShots();
      shots.add(newShot);

      Stats stats = gameService.play(shots);

      dataService.saveShots(shots);
      return stats;
   }

   public interface Action {
      void viewPlay(int frame, int attempt, int pinsLeft);

      void viewScore(Score score);
   }
}
