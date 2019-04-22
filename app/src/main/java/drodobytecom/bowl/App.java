package drodobytecom.bowl;

import android.app.Application;

import drodobytecom.bowl.entity.GameService;
import drodobytecom.bowl.entity.InMemoryGameService;

public class App extends Application {

    GameService service = new InMemoryGameService();

    public GameService service() {
        return service;
    }
}
