package dungeon.model;

/**
 * This interface represents a monster in a text-based adventure game. Monsters get in the way of
 * players and can possibly kill them. Monsters can also be killed by players.
 */
public interface Monster {

  /**
   * This enum represents a monster's health. A monster is either healthy, injured, or death. A
   * healthy monster will kill a player it comes into contact with, an injured monster has 50%
   * chance of killing the player, and obviously a player can simply walk past a dead monster.
   */
  enum Health {
    HEALTHY, INJURED, DEAD;
  }

  /**
   * Returns the current location of the monster.
   *
   * @return location
   */
  Location getLocation();

  /**
   * Sets the monster's location to the given location.
   * @param newLocation new location
   */
  void setLocation(Location newLocation);

  /**
   * Returns the current health of the monster.
   * @return health
   */
  Health getHealth();

  /**
   * Indicates that a monster has been shot, and therefore diminishes its health. If a "healthy"
   * monster is shot, its health is now "injured", if an "injured" monster has been shot, its
   * health is now "dead".
   */
  void shot();

  /**
   * Changes a normal monster to a roaming monster.
   */
  void setRoams();

  /**
   * Tells if the monster roams or not.
   * @return True if roams, false otherwise
   */
  boolean roams();

}