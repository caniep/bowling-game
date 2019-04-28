package drodobytecom.bowl.entity;

import java.util.List;

import static java.util.Collections.unmodifiableList;

class DefaultScore implements Score {

   private List<Frame> frames;

   DefaultScore(List<Frame> frames) {
      this.frames = unmodifiableList(new ScoreCalculator(frames).compute());
   }

   @Override
   public List<Frame> frames() {
      return frames;
   }
}
