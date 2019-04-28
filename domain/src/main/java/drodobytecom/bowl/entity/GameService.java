package drodobytecom.bowl.entity;

/**
 * Simple entity gameService layer
 */
public interface GameService {

   Stats play(Iterable<Integer> shots) throws GameException;
}
