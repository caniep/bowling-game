package drodobytecom.bowl.entity;

import java.util.List;

public interface DataService {

   List<Integer> loadShots();

   void saveShots(List<Integer> shots);
}
