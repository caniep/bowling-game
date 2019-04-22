package drodobytecom.bowl.entity;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.unmodifiableList;

public class Game {

   private final List<Frame> frames;

   Game() {
      frames = new ArrayList<>(10);
      frames.add(new Frame(1));
   }

   public Frame shot(int pinsDown) throws GameException {
      currentFrame().shot(pinsDown);

      if (currentFrame().isDone())
         if (!(currentFrame() instanceof TenthFrame))
            nextFrame();

      return currentFrame();
   }

   public Frame currentFrame() {
      return frames.get(frames.size() - 1);
   }

   public List<Frame> frames() {
      return unmodifiableList(frames);
   }

   public boolean isDone() {
      return currentFrame().number() == 10 && currentFrame().isDone();
   }

   private void nextFrame() {
      int next = currentFrame().number() + 1;
      frames.add(next == 10 ? new TenthFrame() : new Frame(next));
   }
}
