package drodobytecom.bowl.util;

import drodobytecom.bowl.entity.GameException;
import drodobytecom.bowl.entity.GameException.Reason;

import static drodobytecom.bowl.entity.GameException.Reason.InvalidPins;

/**
 * Assert-like helper class for validation
 */
public class Check {

   public static void pins(int pins) {
      Check.between(pins, 0, 10, InvalidPins);
   }

   public static void between(int value, int minInclusive, int maxInclusive, Reason reason) {
      check(value >= minInclusive && value <= maxInclusive, reason);
   }

   private static void check(boolean condition, Reason reason) {
      if (!condition)
         throw new GameException(reason);
   }
}
