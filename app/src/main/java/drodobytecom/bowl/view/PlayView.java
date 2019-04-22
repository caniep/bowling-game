package drodobytecom.bowl.view;

public interface PlayView {

   void showPlay(int frame, int attempt, int pinsLeft);

   void showScore();

   interface Listener {
      void started(PlayView view);

      void pinsDownSelected(PlayView view, int pins);
   }
}
