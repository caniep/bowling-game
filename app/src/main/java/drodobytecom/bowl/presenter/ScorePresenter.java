package drodobytecom.bowl.presenter;

import drodobytecom.bowl.entity.GameService;
import drodobytecom.bowl.entity.DataService;
import drodobytecom.bowl.view.ScoreView;
import drodobytecom.bowl.view.imp.model.ModelScore;

public class ScorePresenter implements ScoreView.Listener {

   private final GameService gameService;
   private final DataService dataService;

   public ScorePresenter(GameService gameService, DataService dataService) {
      this.gameService = gameService;
      this.dataService = dataService;
   }

   @Override
   public void shown(ScoreView view, ModelScore score) {
      view.show(score); // nothing special to do
   }
}
