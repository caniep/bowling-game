package drodobytecom.bowl.view;

import drodobytecom.bowl.view.imp.model.ModelScore;

public interface PlayView {

   void showPlay(int frame, int attempt, int pinsLeft);

   void showScore(ModelScore score);

   interface Listener {
      void started(PlayView view);

      void pinsDownSelected(PlayView view, int pins);
   }
}
