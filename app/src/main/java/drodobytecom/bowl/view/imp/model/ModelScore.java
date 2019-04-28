package drodobytecom.bowl.view.imp.model;

import java.util.List;

public class ModelScore {

   public List<Frame> frames;

   public static class Frame {
      public int firstHit;
      public int secondHit;
      public int thirdHit;
      public int score;
   }
}
