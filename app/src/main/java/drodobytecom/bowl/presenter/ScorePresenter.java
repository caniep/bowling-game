package drodobytecom.bowl.presenter;

import drodobytecom.bowl.entity.GameService;
import drodobytecom.bowl.usecase.ComputeScoreCase;
import drodobytecom.bowl.view.ScoreView;

public class ScorePresenter implements ScoreView.Listener {

   private GameService service;

   public ScorePresenter(GameService service) {
      this.service = service;
   }

   @Override
   public void shown(ScoreView view) {
      new ComputeScoreCase(service).compute(view::show);
   }
}
