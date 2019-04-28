package drodobytecom.bowl.usecase;

import drodobytecom.bowl.entity.GameService;
import drodobytecom.bowl.entity.DataService;

/**
 * Base class for all application use-cases
 */
abstract class AbstractCase {

   protected final GameService gameService;
   protected DataService dataService;

   protected AbstractCase(GameService gameService, DataService dataService) {
      this.gameService = gameService;
      this.dataService = dataService;
   }
}
