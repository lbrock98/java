package dungeon.model;

import java.util.List;
import java.util.Set;

/**
 * This interface represents a location in the dungeon game. The available moves from a location are
 * North, South, East, and West. You can get the available moves in a location, the distance to
 * another location, and print out of the location.
 */

public interface Location {

  /**
   * This enum gives the types of treasure that can be found in caves.
   */
  enum Treasure {
    DIAMOND, RUBY, SAPPHIRE;
  }

  /**
   * This enum gives the possible moves from an abstract location (concrete locations will have
   * a subset of these moves available).
   */
  enum Move {
    NORTH, SOUTH, EAST, WEST;
  }

  /**
   * Returns the moves available from that location (i.e. the direction of the entrances).
   *
   * @return available moves
   */
  Set<Move> getMoves();

  /**
   * Returns the treasure in that location.
   *
   * @return treasure
   */
  List<Treasure> getTreasure();

  /**
   * Adds a move that is available to this location.
   * @param move move
   */
  void addMove(Move move);

  /**
   * Adds treasure to this location at the start of the game.
   * @param treasure treasure
   */
  void addTreasure(Treasure treasure);

  /**
   * Removes treasure from given location.
   */
  void removeTreasure();

  /**
   * Adds one arrow to this location at the start of the game.
   */
  void addArrow();

  /**
   * Removes any arrows from given location.
   */
  void removeArrows();

  /**
   * Gets the number of arrows at that location.
   * @return arrows   number of arrows
   */
  int getArrows();

  /**
   * Adds a monster to a location.
   * @param monster      the monster added
   */
  void addMonster(Monster monster);

  /**
   * Returns the monster object at the given location, if present.
   * @return monster
   */
  Monster getMonster();

  /**
   * Adds a pit to the location.
   */
  void addPit();

  /**
   * Tells if the location has a pit.
   * @return true if loc has pit, false otherwise
   */
  boolean hasPit();

  /**
   * Adds a thief to the location.
   */
  void addThief();

  /**
   * Tells if the location has a thief.
   * @return true if loc has thief, false otherwise
   */
  boolean hasThief();

  /**
   * Tells if the location has been visited.
   * @return  true if the player has entered the loc, false otherwise
   */
  boolean beenVisited();

  /**
   * Called when a player enters this location. Let's the location's visited attribute to true;
   */
  void visit();

  /**
   * Overrides the toString method.
   * @return the location's vertices
   */
  String toString();

}