package drodobytecom.bowl.entity;

import static drodobytecom.bowl.entity.DefaultGameService.MAX_HITS_PER_FRAME;

class DefaultFrame implements Frame {

   private final Integer second;
   private final Integer third;
   private final Integer first;
   private final int score;
   private final boolean isTenth;

   DefaultFrame(boolean isTenth) {
      this((Integer) null, isTenth);
   }

   DefaultFrame(Integer first, boolean isTenth) {
      this(first, null, isTenth);
   }

   DefaultFrame(Integer first, Integer second, boolean isTenth) {
      this(first, second, null, isTenth);
   }

   DefaultFrame(Integer first, Integer second, Integer third, boolean isTenth) {
      this(first, second, third, 0, isTenth);
   }

   DefaultFrame(Frame frame, int score, boolean isTenth) {
      this(frame.hasFirstHit() ? frame.firstHit() : null, frame.hasSecondHit() ? frame.secondHit() : null, frame.hasThirdHit() ? frame.thirdHit() : null, score, isTenth);
   }

   DefaultFrame(Integer first, Integer second, Integer third, int score, boolean isTenth) {
      this.first = first;
      this.second = second;
      this.third = third;
      this.score = score;
      this.isTenth = isTenth;
   }

   @Override
   public int hits() {
      return firstHit() + secondHit() + thirdHit();
   }

   @Override
   public int firstHit() {
      return hasFirstHit() ? first : 0;
   }

   @Override
   public int secondHit() {
      return hasSecondHit() ? second : 0;
   }

   @Override
   public int thirdHit() {
      return hasThirdHit() ? third : 0;
   }

   @Override
   public boolean hasFirstHit() {
      return first != null;
   }

   @Override
   public boolean hasSecondHit() {
      return second != null;
   }

   @Override
   public boolean hasThirdHit() {
      return third != null;
   }

   @Override
   public int score() {
      return score;
   }

   @Override
   public boolean isDone() {
      if (!isTenth)
         return isStrike() || hasSecondHit();
      if (!hasSecondHit())
         return false;
      return hasThirdHit() || firstHit() + secondHit() < MAX_HITS_PER_FRAME;
   }

   private boolean isStrike() {
      return hasFirstHit() && firstHit() == MAX_HITS_PER_FRAME && !hasSecondHit() && !hasThirdHit();
   }
}
