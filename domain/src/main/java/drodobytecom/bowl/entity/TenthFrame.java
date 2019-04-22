package drodobytecom.bowl.entity;

import drodobytecom.bowl.util.Check;

class TenthFrame extends Frame {

   TenthFrame() {
      super(10);
   }

   @Override
   public boolean isDone() {
      return attempts.size() == 3 || (attempts.size() == 2 && pinsDown() < 10);
   }

   @Override
   public int pinsLeft() {
      return 10 - pinsDown() % 10;
   }

   @Override
   void shot(int pinsDown) {
      Check.pins(pinsDown);

      attempts.add(new Attempt(pinsDown));
   }
}
