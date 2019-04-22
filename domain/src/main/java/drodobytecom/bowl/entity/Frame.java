package drodobytecom.bowl.entity;

import java.util.ArrayList;
import java.util.List;

import drodobytecom.bowl.util.Check;

import static java.util.Collections.unmodifiableList;

public class Frame {
   final List<Attempt> attempts;
   private final int number;

   Frame(int number) {
      this.number = number;
      attempts = new ArrayList<>(3);
   }

   public int number() {
      return number;
   }

   public List<Attempt> attempts() {
      return unmodifiableList(attempts);
   }

   public boolean isDone() {
      return isStrike() || isSpare() || !hasMoreAttempts();
   }

   public boolean isStrike() {
      return pinsDown(0) == 10;
   }

   public boolean isSpare() {
      return pinsDown(0, 1) == 10;
   }

   public int pinsDown() {
      return pinsDown(0, 1, 2);
   }

   public int pinsLeft() {
      return 10 - pinsDown();
   }

   void shot(int pinsDown) {
      Check.pins(pinsDown);
      Check.pins(pinsDown + pinsDown());

      attempts.add(new Attempt(pinsDown));
   }

   int pinsDown(int attempt) {
      return attempts.size() > attempt ? attempts.get(attempt).pinsDown() : 0;
   }

   int pinsDown(int... attempts) {
      int down = 0;
      for (int attempt : attempts)
         down += pinsDown(attempt);
      return down;
   }

   private boolean hasMoreAttempts() {
      return attempts.size() < 2;
   }

   public class Attempt {
      private final int pinsDown;

      Attempt(int pinsDown) {
         this.pinsDown = pinsDown;
      }

      public int pinsDown() {
         return pinsDown;
      }
   }
}
