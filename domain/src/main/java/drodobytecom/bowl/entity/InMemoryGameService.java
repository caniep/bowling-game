package drodobytecom.bowl.entity;

import java.util.ArrayList;
import java.util.List;

import drodobytecom.bowl.util.Check;

import static java.util.Collections.unmodifiableList;

/**
 * Default implementation for the {@link GameService}, useful for testing the use-cases
 */
public class InMemoryGameService implements GameService {

   private Game game;

   @Override
   public Game game(boolean newGame) {
      return newGame ? new GameImp() : game;
   }

   @Override
   public void save(Game game) {
      this.game = game;
   }

   static class GameImp implements Game {

      private final List<FrameImp> frames;

      GameImp() {
         frames = new ArrayList<>(10);
         frames.add(new FrameImp(1));
      }

      @Override
      public Frame shot(int pinsDown) throws GameException {
         currentFrame().shot(pinsDown);

         if (currentFrame().isDone())
            if (!(currentFrame() instanceof TenthFrame))
               nextFrame();

         return currentFrame();
      }

      @Override
      public FrameImp currentFrame() {
         return frames.get(frames.size() - 1);
      }

      @Override
      public List<Frame> frames() {
         return unmodifiableList(frames);
      }

      @Override
      public boolean isDone() {
         return currentFrame().number() == 10 && currentFrame().isDone();
      }

      private void nextFrame() {
         int next = currentFrame().number() + 1;
         frames.add(next == 10 ? new TenthFrame() : new FrameImp(next));
      }
   }

   static class FrameImp implements Game.Frame {
      final List<Attempt> attempts;
      private final int number;

      FrameImp(int number) {
         this.number = number;
         attempts = new ArrayList<>(3);
      }

      @Override
      public int number() {
         return number;
      }

      @Override
      public List<Attempt> attempts() {
         return unmodifiableList(attempts);
      }

      @Override
      public boolean isDone() {
         return isStrike() || isSpare() || !hasMoreAttempts();
      }

      @Override
      public boolean isStrike() {
         return pinsDown(0) == 10;
      }

      @Override
      public boolean isSpare() {
         return pinsDown(0, 1) == 10;
      }

      @Override
      public int pinsDown() {
         return pinsDown(0, 1, 2);
      }

      @Override
      public int pinsLeft() {
         return 10 - pinsDown();
      }

      void shot(int pinsDown) {
         Check.pins(pinsDown);
         Check.pins(pinsDown + pinsDown());

         attempts.add(new AttemptImp(pinsDown));
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

      class AttemptImp implements Attempt {
         private final int pinsDown;

         AttemptImp(int pinsDown) {
            this.pinsDown = pinsDown;
         }

         @Override
         public int pinsDown() {
            return pinsDown;
         }
      }
   }

   /**
    * The tenth frame is a {@link drodobytecom.bowl.entity.Game.Frame} with some different logic
    */
   static class TenthFrame extends FrameImp {

      TenthFrame() {
         super(10);
      }

      @Override
      public boolean isDone() {
         return attempts.size() == 3 || (attempts.size() == 2 && pinsDown() < 10);
      }

      @Override
      public int pinsLeft() {
         return 10 - pinsDown() % 10;
      }

      @Override
      void shot(int pinsDown) {
         Check.pins(pinsDown);

         attempts.add(new AttemptImp(pinsDown));
      }
   }
}
