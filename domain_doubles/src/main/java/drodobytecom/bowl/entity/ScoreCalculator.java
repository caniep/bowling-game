package drodobytecom.bowl.entity;

import java.util.ArrayList;
import java.util.List;

class ScoreCalculator {

   private List<Frame> frames;
   private List<Frame> computed;

   ScoreCalculator(List<Frame> frames) {
      this.frames = frames;
   }

   List<Frame> compute() {
      computed = new ArrayList<>(this.frames.size());
      for (int frame = 0; frame < frames.size(); frame++)
         computed.add(compute(frame, frame == 9));
      return computed;
   }

   private Frame compute(int frame, boolean isTenth) {
      return new DefaultFrame(get(frame), score(frame), isTenth);
   }

   private int score(int frame) {
      int previous = previousScore(frame);
      int actual = get(frame).hits();
      if (isStrike(frame))
         actual = 10 + nextTwoScores(frame);
      else if (isSpare(frame))
         actual = 10 + nextScore(frame);
      return previous + actual;
   }

   private int previousScore(int frame) {
      return frame == 0 ? 0 : computed.get(frame - 1).score();
   }

   private int nextTwoScores(int frame) {
      if (frame == 9)
         return secondHit(frame) + thirdHit(frame);
      else
         return firstHit(frame + 1) +
                 (!isStrike(frame + 1) ? secondHit(frame + 1) : firstHit(frame + 2));
   }

   private int nextScore(int frame) {
      return frame == 9 ? secondHit(frame) : firstHit(frame + 1);
   }

   private boolean isStrike(int frame) {
      return firstHit(frame) == 10 && !get(frame).hasSecondHit();
   }

   private boolean isSpare(int frame) {
      return firstHit(frame) + secondHit(frame) == 10 && !get(frame).hasThirdHit();
   }

   private int firstHit(int frame) {
      return has(frame) ? get(frame).firstHit() : 0;
   }

   private int secondHit(int frame) {
      return has(frame) ? get(frame).secondHit() : 0;
   }

   private int thirdHit(int frame) {
      return has(frame) ? get(frame).thirdHit() : 0;
   }

   private boolean has(int frame) {
      return frames.size() > frame;
   }

   private Frame get(int frame) {
      return frames.get(frame);
   }
}
