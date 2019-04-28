package drodobytecom.bowl.entity;

public class GameException extends RuntimeException {

   private final Reason reason;

   public GameException(Reason reason) {
      this.reason = reason;
   }

   public Reason getReason() {
      return reason;
   }

   @Override
   public String toString() {
      return super.toString() + ": " + reason;
   }

   public enum Reason {
      InvalidPlays, InvalidTenthFrame, InvalidPins
   }
}
