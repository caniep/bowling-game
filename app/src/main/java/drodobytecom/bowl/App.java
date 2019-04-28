package drodobytecom.bowl;

import android.app.Application;

import drodobytecom.bowl.entity.DefaultDataService;
import drodobytecom.bowl.entity.DefaultGameService;
import drodobytecom.bowl.entity.GameService;
import drodobytecom.bowl.entity.DataService;

public class App extends Application {

   GameService gameService = new DefaultGameService();
   DataService dataService = new DefaultDataService();

   public GameService gameService() {
      return gameService;
   }

   public DataService dataService() {
      return dataService;
   }
}
