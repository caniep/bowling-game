package drodobytecom.bowl.util;

import java.util.List;

import drodobytecom.bowl.entity.Frame;
import drodobytecom.bowl.entity.GameException;
import drodobytecom.bowl.entity.GameException.Reason;

import static drodobytecom.bowl.entity.GameException.Reason.InvalidPins;
import static drodobytecom.bowl.entity.GameException.Reason.InvalidPlays;
import static drodobytecom.bowl.entity.GameException.Reason.InvalidTenthFrame;

/**
 * Assert-like helper class for validation
 */
public class Check {

   public static void frames(List<Frame> frames) {
      between(frames.size(), 0, 10, InvalidPlays);
      for (int i = 0; i < frames.size(); i++)
         frame(frames.get(i), i == 9);
   }

   private static void frame(Frame frame, boolean isTenthFrame) {
      pins(frame.firstHit());
      pins(frame.secondHit());
      pins(frame.thirdHit());
      if (isTenthFrame)
         tenthFrame(frame);
      else
         pins(frame.hits());
   }

   private static void tenthFrame(Frame frame) {
      if (frame.firstHit() + frame.secondHit() < 10)
         check(!frame.hasThirdHit(), InvalidTenthFrame);
   }

   private static void pins(int pins) {
      Check.between(pins, 0, 10, InvalidPins);
   }

   private static void between(int value, int minInclusive, int maxInclusive, Reason reason) {
      check(value >= minInclusive && value <= maxInclusive, reason);
   }

   private static void check(boolean condition, Reason reason) {
      if (!condition)
         throw new GameException(reason);
   }
}
