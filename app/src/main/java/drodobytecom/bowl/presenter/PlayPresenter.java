package drodobytecom.bowl.presenter;

import drodobytecom.bowl.entity.GameService;
import drodobytecom.bowl.entity.Score;
import drodobytecom.bowl.usecase.PlayOneShotCase;
import drodobytecom.bowl.usecase.StartGameCase;
import drodobytecom.bowl.entity.DataService;
import drodobytecom.bowl.view.PlayView;
import drodobytecom.bowl.view.imp.model.ModelScoreAdapter;

import static drodobytecom.bowl.usecase.PlayOneShotCase.Action;

public class PlayPresenter implements PlayView.Listener {

   private final GameService gameService;
   private final DataService dataService;

   public PlayPresenter(GameService gameService, DataService dataService) {
      this.gameService = gameService;
      this.dataService = dataService;
   }

   @Override
   public void started(PlayView view) {
      new StartGameCase(gameService, dataService).start(view::showPlay);
   }

   @Override
   public void pinsDownSelected(PlayView view, int pins) {
      new PlayOneShotCase(gameService, dataService).shot(pins, new Action() {
         @Override
         public void viewPlay(int frame, int attempt, int pinsLeft) {
            view.showPlay(frame, attempt, pinsLeft);
         }

         @Override
         public void viewScore(Score score) {
            view.showScore(new ModelScoreAdapter().adapt(score));
         }
      });
   }
}
