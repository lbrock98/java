package dungeon.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class represents a player in the dungeon game. It implements the Player interface.
 */

public class PlayerImpl implements Player {
  private ArrayList<Location.Treasure> treasure;
  private Location location;
  private int arrows;
  private boolean health;


  /**
   * Construct a Player, setting their list of treasure to empty, and their location to null.
   */
  public PlayerImpl() {
    this.treasure = new ArrayList<>();
    this.location = null;
    this.arrows = 3;
    this.health = true;
  }

  @Override
  public boolean getHealth() {
    boolean health = this.health;
    return health;
  }

  @Override
  public void move(Location newLocation) {
    this.location = newLocation;
    newLocation.visit();
    if (newLocation.hasPit()) {
      this.health = false;
    }
    if (newLocation.getMonster() != null) {
      Monster.Health monsterHealth = newLocation.getMonster().getHealth();
      if (monsterHealth.name().equals("HEALTHY")) {
        this.health = false;
      } else if (monsterHealth.name().equals("INJURED") || newLocation.getMonster().roams()) {
        Random random = new Random();
        Boolean ran = random.nextBoolean();
        if (ran) {
          this.health = false;
        }
      }
    }
    if (newLocation.hasThief()) {
      Random randomizer = new Random();
      if (this.treasure.size() > 0) {
        int rand = randomizer.nextInt(this.treasure.size());
        this.treasure.remove(rand);
      }
    }
  }

  @Override
  public ArrayList<Location.Treasure> getTreasure() {
    ArrayList<Location.Treasure> treasures = this.treasure;
    return treasures;
  }

  @Override
  public Location getLocation() {
    Location loc = this.location;
    return loc;
  }

  @Override
  public void collectTreasure() {
    List<Location.Treasure> treasures = location.getTreasure();
    for (Location.Treasure newTreasure : treasures) {
      this.treasure.add(newTreasure);
    }
    location.removeTreasure();
  }

  @Override
  public void collectArrows() {
    this.arrows += this.location.getArrows();
    this.location.removeArrows();
  }

  @Override
  public int getArrows() {
    int arrows = this.arrows;
    return arrows;
  }

  @Override
  public void removeArrow() {
    this.arrows--;
  }
}