package drodobytecom.bowl.view;

import drodobytecom.bowl.view.imp.model.ModelScore;

public interface ScoreView {

   void show(ModelScore score);

   interface Listener {
      void shown(ScoreView view, ModelScore score);
   }
}
