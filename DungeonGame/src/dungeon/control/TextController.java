package dungeon.control;

import dungeon.model.Dungeon;

/**
 * Represents a controller for the dungeon game. Handles user inputs by executing the model.
 */
public interface TextController {

  /**
   * Executes a dungeon game with the given model.
   */
  void game(Dungeon dungeon);
}