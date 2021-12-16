package dungeon.model;

/**
 * The interface for a read-only model of the Dungeon.
 */
public interface ReadOnlyDungeon {

  /**
   * Returns the player object created when the dungeon is constructed.
   * @return player     the player in the dungeon
   */
  Player getPlayer();

  int[] getDimensions();

  /**
   * Gets the location at the given x and y coordinates.
   * @param x x coordinate
   * @param y y coordinate
   * @return location at (x,y)
   */
  Location getLocation(int x, int y);

  /**
   * Describes the given object.
   * @return description
   */
  String description(Object o);

  /**
   * Returns the end location of the dungeon.
   * @return end location
   */
  Location getEnd();
}
