package drodobytecom.bowl.entity;

public interface Stats {

   Score score();

   boolean isGameDone();

   int nextFrame();

   int nextAttempt();

   int nextPinsLeft();
}
