package drodobytecom.bowl.usecase;

import drodobytecom.bowl.entity.GameService;

/**
 * Base class for all application use-cases
 */
abstract class AbstractCase {

   protected final GameService service;

   protected AbstractCase(GameService service) {
      this.service = service;
   }
}
