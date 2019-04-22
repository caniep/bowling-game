package drodobytecom.bowl.usecase.adapter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import drodobytecom.bowl.entity.Frame;
import drodobytecom.bowl.entity.Frame.Attempt;
import drodobytecom.bowl.entity.Game;
import drodobytecom.bowl.usecase.model.Score;
import drodobytecom.bowl.util.Adapter;

/**
 * Adapts an {@link Score} from a {@link Game} match
 */
public class GameScoreAdapter implements Adapter<Game, Score> {

   private Score score;
   private Game game;

   @Override
   public Score adapt(Game game) {
      this.game = game;
      score = new Score();
      for (int frame = 0; frame < game.frames().size(); frame++)
         score.frames.add(adapt(game.frames().get(frame)));
      return score;
   }

   private Score.Frame adapt(Frame frame) {
      List<Attempt> attempts = frame.attempts();
      int firstAttempt = attempts.get(0).pinsDown();
      int secondAttempt = attempts.size() > 1 ? attempts.get(1).pinsDown() : 0;
      int thirdAttempt = attempts.size() > 2 ? attempts.get(2).pinsDown() : 0;
      int score = score(frame);
      return new Score.Frame(firstAttempt, secondAttempt, thirdAttempt, score);
   }

   private int score(Frame frame) {
      int previous = previousScore(frame);
      int actual = frame.pinsDown();
      if (frame.isStrike())
         actual = 10 + nextTwoAttemptScores(frame);
      else if (frame.isSpare())
         actual = 10 + nextAttemptScore(frame);
      return previous + actual;
   }

   private int previousScore(Frame frame) {
      return frame.number() == 1 ? 0 : score.frames.get(frame.number() - 2).score;
   }

   private int nextTwoAttemptScores(Frame frame) {
      return addAttemptScores(mergeNextAttempts(2, frame));
   }

   private int nextAttemptScore(Frame frame) {
      return addAttemptScores(mergeNextAttempts(1, frame));
   }

   private List<Attempt> mergeNextAttempts(int count, Frame frame) {
      if (frame.number() == 10) {
         return count == 1 ? frame.attempts().subList(2, 3) : frame.attempts().subList(1, 3);
      } else {
         Frame next = game.frames().get(frame.number());
         List<Attempt> attempts = new ArrayList<>(count);
         while (count > 0) {
            Iterator<Attempt> it = next.attempts().iterator();
            while (it.hasNext() && count-- > 0)
               attempts.add(it.next());
            if (count > 0)
               next = game.frames().get(next.number());
         }
         return attempts;
      }
   }

   private int addAttemptScores(List<Attempt> attempts) {
      int score = 0;
      for (Attempt attempt : attempts)
         score += attempt.pinsDown();
      return score;
   }
}
