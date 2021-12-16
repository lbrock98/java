package dungeon.model;

import static dungeon.model.Monster.Health.DEAD;
import static dungeon.model.Monster.Health.INJURED;

/**
 * This class represents an Otyugh and implements the Monster interface. Otyughs are extremely
 * smelly creatures that lead solitary lives in the deep, dark places of the world like our dungeon.
 * They occupy caves, and can be detected by their smell. There is a less pungent smell if one
 * Otyugh is 2 locations away.
 */
public class Otyugh implements Monster {
  private Location location;
  private Health health;
  private boolean roaming;

  /**
   * Constructs an Otyugh, setting its location to null, and its health to healthy.
   */
  public Otyugh() {
    this.location = null;
    this.health = Health.HEALTHY;
    this.roaming = false;
  }

  @Override
  public Location getLocation() {
    Location currentLoc = this.location;
    return currentLoc;
  }

  @Override
  public void setLocation(Location newLocation) {
    this.location = newLocation;
  }

  @Override
  public Health getHealth() {
    Health health = this.health;
    return health;
  }

  @Override
  public void shot() {
    switch (this.health.name()) {
      case "HEALTHY":
        this.health = INJURED;
        break;
      case "INJURED":
        this.health = DEAD;
        break;
      default:
        break;
    }
  }

  @Override
  public void setRoams() {
    this.roaming = true;
  }

  @Override
  public boolean roams() {
    boolean roams = this.roaming;
    return roams;
  }

}