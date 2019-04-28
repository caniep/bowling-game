package drodobytecom.bowl.entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import drodobytecom.bowl.util.Adapter;

class ShotsFramesAdapter implements Adapter<Iterable<Integer>, List<Frame>> {

   private final int maxHits;

   ShotsFramesAdapter(int maxHits) {
      this.maxHits = maxHits;
   }

   @Override
   public List<Frame> adapt(Iterable<Integer> shots) {
      List<Frame> frames = new ArrayList<>();
      fillFrames(shots.iterator(), frames);
      return frames;
   }

   private void fillFrames(Iterator<Integer> it, List<Frame> frames) {
      while (it.hasNext())
         frames.add(asFrame(it, isTenthFrame(frames)));
   }

   private Frame asFrame(Iterator<Integer> it, boolean tenthFrame) {
      if (tenthFrame)
         return new DefaultFrame(next(it), next(it), next(it), tenthFrame);
      else {
         int hit = it.next();
         return hit == maxHits ? new DefaultFrame(hit, tenthFrame) : new DefaultFrame(hit, next(it), tenthFrame);
      }
   }

   private boolean isTenthFrame(List<Frame> frames) {
      return frames.size() == 9;
   }

   private Integer next(Iterator<Integer> it) {
      return it.hasNext() ? it.next() : null;
   }
}
