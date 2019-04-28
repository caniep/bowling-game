package drodobytecom.bowl.usecase;

import org.junit.jupiter.api.BeforeEach;

import drodobytecom.bowl.entity.DataService;
import drodobytecom.bowl.entity.DefaultDataService;
import drodobytecom.bowl.entity.DefaultGameService;
import drodobytecom.bowl.entity.GameService;
import drodobytecom.bowl.entity.Score;
import drodobytecom.bowl.usecase.PlayOneShotCase.Action;

abstract class AbstractTest {

   private GameService gameService;
   private DataService dataService;

   @BeforeEach
   void init() {
      gameService = new DefaultGameService();
      dataService = new DefaultDataService();
   }

   void start(StartGameCase.Action action) {
      new StartGameCase(gameService, dataService).start(action);
   }

   void play(int[] shots, Action action) {
      playRecursive(shots, 0, action);
   }

   int[] shots(int... shots) {
      return shots;
   }

   private void playRecursive(int[] shots, int i, Action action) {
      if (i < shots.length)
         new PlayOneShotCase(gameService, dataService).shot(shots[i], new Action() {
            @Override
            public void viewPlay(int frame, int attempt, int pinsLeft) {
               action.viewPlay(frame, attempt, pinsLeft);
               playRecursive(shots, i + 1, action);
            }

            @Override
            public void viewScore(Score score) {
               if (tooMuchShots())
                  playRecursive(shots, i + 1, action);
               else
                  action.viewScore(score);
            }

            // erroneous condition, we need to fake call to raise GameException
            private boolean tooMuchShots() {
               return i + 1 < shots.length;
            }
         });
   }
}
