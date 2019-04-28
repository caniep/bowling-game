package drodobytecom.bowl.entity;

import java.util.ArrayList;
import java.util.List;

public class DefaultDataService implements DataService {

   private List<Integer> shots;

   @Override
   public List<Integer> loadShots() {
      return shots == null ? new ArrayList<>() : shots;
   }

   @Override
   public void saveShots(List<Integer> shots) {
      this.shots = shots;
   }
}
