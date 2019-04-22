package drodobytecom.bowl.usecase;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;

import drodobytecom.bowl.entity.GameService;
import drodobytecom.bowl.entity.InMemoryGameService;

abstract class AbstractTest {

   @Mock
   GameService service;

   @BeforeEach
   void init() {
      service = new InMemoryGameService();
   }

   void startGame(StartGameCase.Action action) {
      new StartGameCase(service).start(action);
   }

   void playOneShot(int pinsDown, PlayOneShotCase.Action action) {
      new PlayOneShotCase(service).shot(pinsDown, action);
   }

   void playShots(int[] pinsDownPerShot, PlayOneShotCase.Action actionOnLastShot) {
      playShotsRecursive(pinsDownPerShot, 0, actionOnLastShot);
   }

   int[] shots(int... shots) {
      return shots;
   }

   void computeScore(ComputeScoreCase.Action action) {
      new ComputeScoreCase(service).compute(action);
   }

   void computeScore(int[] shots, ComputeScoreCase.Action action) {
      playShots(shots, new PlayOneShotCase.Action() {
         @Override
         public void viewPlay(int frame, int attempt, int pinsLeft) {
         }

         @Override
         public void viewScore() {
            computeScore(score -> action.show(score));
         }
      });
   }

   private void playShotsRecursive(int[] shots, int index, PlayOneShotCase.Action action) {
      if (index == shots.length - 1)
         playOneShot(shots[index], action);
      else
         playOneShot(shots[index], new PlayOneShotCase.Action() {
            @Override
            public void viewPlay(int frame, int attempt, int pinsLeft) {
               playShotsRecursive(shots, index + 1, action);
            }

            @Override
            public void viewScore() {
               action.viewScore();
            }
         });
   }
}
