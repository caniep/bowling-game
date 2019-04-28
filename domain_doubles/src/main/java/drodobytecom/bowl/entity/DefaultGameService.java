package drodobytecom.bowl.entity;

public class DefaultGameService implements GameService {

   static final int MAX_FRAMES = 10;
   static final int MAX_HITS_PER_FRAME = 10;

   @Override
   public Stats play(Iterable<Integer> shots) {
      return newStats(shots);
   }

   private Stats newStats(Iterable<Integer> shots) {
      return new DefaultStats(new ShotsScoreAdapter(MAX_HITS_PER_FRAME).adapt(shots));
   }
}
