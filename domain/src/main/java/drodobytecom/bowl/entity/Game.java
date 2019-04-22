package drodobytecom.bowl.entity;

import java.util.List;

/**
 * The bowling game containing the frames and logic for throwing balls
 */
public interface Game {

   /**
    * Throws a ball
    *
    * @param pinsDown pins hit by the move
    * @return the {@link Frame} with the stats
    * @throws GameException is pinsDown is invalid {@link GameException.Reason}
    */
   Frame shot(int pinsDown) throws GameException;

   Frame currentFrame();

   List<Frame> frames();

   /**
    * @return true when game is finished
    */
   boolean isDone();

   /**
    * Contains the stats of the frame, and the throw attempts done by the player
    */
   interface Frame {

      int number();

      List<Attempt> attempts();

      boolean isDone();

      boolean isStrike();

      boolean isSpare();

      int pinsDown();

      int pinsLeft();

      /**
       * Pins down on a single attempt
       */
      interface Attempt {

         int pinsDown();
      }
   }
}
