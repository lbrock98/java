package dungeon.model;

import java.util.ArrayList;

/**
 * This interface represents a dungeon that is created to play a game. A dungeon consists of a
 * network of tunnels and caves that are interconnected so that a player can explore the entire
 * world by traveling from cave to cave through the tunnels that connect them. Some caves have
 * treasure.
 */

public interface Dungeon extends ReadOnlyDungeon {

  /**
   * This enum represents what a player can smell from their current location, which is an
   * indication of their proximity to monsters. If one monster one location away from the player or
   *  two+ monsters two locations away from the player, then the smell is strong. If there is one
   *  monster two locations away from the player, then the smell is weak. Otherwise there is no
   *  smell.
   */
  enum Smell {
    STRONG, WEAK, NONE;
  }

  /**
   * This enum represents what a player can hear from their current location, which is an
   * indication of their proximity to a wolf pit. If a wolf pit is at least 4 locations away, the
   * player will hear a faint howl, if the wolf pit is one location away, the player will hear a
   * loud howl.
   */
  enum Howl {
    LOUD, FAINT, NONE;
  }

  /**
   * Returns the start location of the dungeon.
   * @return start location
   */
  Location getStart();

  /**
   * Returns a string that allows you to view the dungeon.
   * @return dungeon
   */
  String printDungeon();

  /**
   * Returns the path from location one to location two.
   * @param one location one
   * @param two location two
   * @return path
   */
  ArrayList<Location> getPath(Location one, Location two);

  /**
   * Indicates that a player has shot an arrow in the dungeon in the given direction and for the
   * given distance.
   * @param direction   direction in which the arrow is shot
   * @param distance    distance at which the arrow is shot
   * @return true if a monster was hit, false otherwise
   */
  boolean arrowShot(Location.Move direction, int distance);

  /**
   * Finds the distance between the monsters in the cave and the player. Returns the smell that
   * indicates how close the monsters are, per the enum in this interface.
   * @return smell
   */
  Smell findMonsters();

  /**
   * Indicates the distance between the player's current location and the wolf pit.
   * @return    howl, if the wolf pit is at least 4 locations away, the player will hear a faint
   *            howl, if the wolf pit is one location away, the player will hear a loud howl.
   */
  Howl findPit();

  /**
   * Returns the location arrived at by starting at the given location and taking the given move.
   * @param location    start location
   * @param move    indicated move
   * @return    end location
   * @throws IllegalArgumentException if the given move is not valid for the given location
   */
  Location newLoc(Location location, Location.Move move);

  /**
   * Moves the roaming monster to an available adjacent location, if that location doesn't have
   * another monster.
   * @throws  IllegalStateException if this is called on a stationary monster
   */
  void monsterRoams();

  /**
   * Cheat code that marks all the dungeon's locations as visited.
   */
  void cheat();
}
