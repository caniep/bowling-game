package drodobytecom.bowl.view;

import drodobytecom.bowl.usecase.model.Score;

public interface ScoreView {

   void show(Score score);

   interface Listener {
      void shown(ScoreView view);
   }
}
