package drodobytecom.bowl.entity;

public interface Frame {

   boolean isDone();

   int hits();

   int firstHit();

   int secondHit();

   int thirdHit();

   boolean hasFirstHit();

   boolean hasSecondHit();

   boolean hasThirdHit();

   int score();
}
