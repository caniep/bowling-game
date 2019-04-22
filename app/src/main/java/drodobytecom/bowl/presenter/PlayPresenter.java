package drodobytecom.bowl.presenter;

import drodobytecom.bowl.entity.GameService;
import drodobytecom.bowl.usecase.PlayOneShotCase;
import drodobytecom.bowl.usecase.StartGameCase;
import drodobytecom.bowl.view.PlayView;

import static drodobytecom.bowl.usecase.PlayOneShotCase.Action;

public class PlayPresenter implements PlayView.Listener {

   private final GameService service;

   public PlayPresenter(GameService service) {
      this.service = service;
   }

   @Override
   public void started(PlayView view) {
      new StartGameCase(service).start(frame ->
              view.showPlay(frame.number(), 1, frame.pinsLeft()));
   }

   @Override
   public void pinsDownSelected(PlayView view, int pins) {
      new PlayOneShotCase(service).shot(pins, new Action() {
         @Override
         public void viewPlay(int frame, int attempt, int pinsLeft) {
            view.showPlay(frame, attempt, pinsLeft);
         }

         @Override
         public void viewScore() {
            view.showScore();
         }
      });
   }
}
