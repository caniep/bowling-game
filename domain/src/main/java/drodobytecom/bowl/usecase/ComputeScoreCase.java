package drodobytecom.bowl.usecase;

import drodobytecom.bowl.entity.GameService;
import drodobytecom.bowl.usecase.adapter.GameScoreAdapter;
import drodobytecom.bowl.usecase.model.Score;

public class ComputeScoreCase extends AbstractCase {

   public ComputeScoreCase(GameService service) {
      super(service);
   }

   public void compute(Action action) {
      action.show(computeScore());
   }

   private Score computeScore() {
      return new GameScoreAdapter().adapt(service.game(false));
   }

   public interface Action {
      void show(Score score);
   }
}
