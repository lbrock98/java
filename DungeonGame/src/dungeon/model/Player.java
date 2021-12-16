package dungeon.model;

import java.util.List;

/**
 * This interface represents a player that can enter the dungeon game. The player explores the
 * dungeon. You can get the treasure in the player's possession, the position of the player, and
 * have the player collect treasure.
 */

public interface Player {

  /**
   * Returns a list of treasure that the player currently has. Will be used later to give a
   * description of the player.
   *
   * @return list of treasure
   */
  List<Location.Treasure> getTreasure();

  /**
   * Returns the current location of the player.
   *
   * @return location
   */
  Location getLocation();

  /**
   * Adds treasure at the player's current location to the player's list of treasure. Updates the
   * location so that it no longer has that treasure.
   */
  void collectTreasure();

  /**
   * Moves the player to the given location.
   * @param location    location where the player is moved to
   */
  void move(Location location);

  /**
   * Adds an arrow to the player's quiver if one is found in the player's location. Updates the
   * location so that it no longer has that arrow.
   */
  void collectArrows();

  /**
   * Returns the number of arrows the player has left.
   * @return arrows
   */
  int getArrows();

  /**
   * Gets the player's current health.
   * @return boolean, true if player is alive, false if player is dead
   */
  boolean getHealth();

  /**
   * Removes an arrow from the player's quiver after the player has shot an arrow.
   */
  void removeArrow();

}