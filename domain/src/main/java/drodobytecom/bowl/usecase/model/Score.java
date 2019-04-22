package drodobytecom.bowl.usecase.model;

import java.util.ArrayList;
import java.util.List;

public class Score {

   public final List<Frame> frames = new ArrayList<>(10);

   public static class Frame {
      public final int pinsDownFirstAttempt;
      public final int pinsDownSecondAttempt;
      public final int pinsDownThirdAttempt;
      public final int score;

      public Frame(int firstAttempt, int secondAttempt, int score) {
         this(firstAttempt, secondAttempt, 0, score);
      }

      public Frame(int firstAttempt, int secondAttempt, int thirdAttempt, int score) {
         this.pinsDownFirstAttempt = firstAttempt;
         this.pinsDownSecondAttempt = secondAttempt;
         this.pinsDownThirdAttempt = thirdAttempt;
         this.score = score;
      }
   }
}
