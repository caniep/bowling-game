package drodobytecom.bowl.entity;

import java.util.List;

import static drodobytecom.bowl.entity.DefaultGameService.MAX_FRAMES;
import static drodobytecom.bowl.entity.DefaultGameService.MAX_HITS_PER_FRAME;

class DefaultStats implements Stats {
   private Score score;

   DefaultStats(Score score) {
      this.score = score;
   }

   @Override
   public Score score() {
      return score;
   }

   @Override
   public boolean isGameDone() {
      return isTenthFrame() && frame().isDone();

   }
   @Override
   public int nextFrame() {
      if (noFrames())
         return 1;
      else
         return frameCount() + (frame().isDone() ? 1 : 0);
   }

   @Override
   public int nextAttempt() {
      if (noFrames())
         return 1;
      else
         return frame().isDone() ? 1 : isTenthFrame() ? (frame().hasSecondHit() ? 3 : 2) : 2;
   }

   @Override
   public int nextPinsLeft() {
      if (isTenthFrame() && frame().hits() >= MAX_HITS_PER_FRAME)
         return MAX_HITS_PER_FRAME;
      return MAX_HITS_PER_FRAME - (noFrames() || frame().isDone() ? 0 : frame().hits());
   }

   private List<Frame> frames() {
      return score.frames();
   }

   private Frame frame() {
      return frames().get(frameCount() - 1);
   }

   private boolean noFrames() {
      return frames().isEmpty();
   }

   private int frameCount() {
      return frames().size();
   }

   private boolean isTenthFrame() {
      return frameCount() == MAX_FRAMES;
   }
}
