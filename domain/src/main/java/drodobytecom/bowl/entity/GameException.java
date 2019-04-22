package drodobytecom.bowl.entity;

public class GameException extends RuntimeException {

   private final Reason reason;

   public GameException(Reason reason) {
      this.reason = reason;
   }

   public Reason getReason() {
      return reason;
   }

   public enum Reason {
      InvalidPins
   }
}
