package drodobytecom.bowl.entity;

import java.util.List;

import drodobytecom.bowl.util.Adapter;
import drodobytecom.bowl.util.Check;

class ShotsScoreAdapter implements Adapter<Iterable<Integer>, Score> {

   private final int maxHits;

   ShotsScoreAdapter(int maxHits) {
      this.maxHits = maxHits;
   }

   @Override
   public Score adapt(Iterable<Integer> shots) {
      List<Frame> frames = new ShotsFramesAdapter(maxHits).adapt(shots);
      Check.frames(frames);
      return new DefaultScore(frames);
   }
}
