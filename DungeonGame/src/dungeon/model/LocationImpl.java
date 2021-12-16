package dungeon.model;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * This class represents a location in the dungeon. It implements the Location interface. A location
 * can be either a tunnel or a cave, a tunnel having 2 entrances, and a cave having 1, 3, or 4
 * entrances. Caves can contain treasure.
 */

public class LocationImpl implements Location {
  private final int xCoord;
  private final int yCoord;
  private HashSet<Move> moves;
  private ArrayList<Treasure> treasure;
  private int arrows;
  private Monster monster;
  private boolean pit;
  private boolean thief;
  private boolean visited;

  /**
   * Constructs a location with the given coordinates.
   *
   * @param x    x coordinate
   * @param y    y coordinate
   */
  public LocationImpl(int x, int y) {
    this.xCoord = x;
    this.yCoord = y;
    this.moves = new HashSet<Move>();
    this.treasure = new ArrayList<Treasure>();
    this.arrows = 0;
    this.monster = null;
    this.pit = false;
    this.thief = false;
    this.visited = false;
  }

  @Override
  public void addMove(Move move) {
    moves.add(move);
  }

  @Override
  public HashSet<Move> getMoves() {
    HashSet<Move> moves = this.moves;
    return moves;
  }

  @Override
  public ArrayList<Treasure> getTreasure() {
    ArrayList<Treasure> treasure = this.treasure;
    return treasure;
  }

  @Override
  public void removeTreasure() {
    this.treasure = new ArrayList<>();
  }

  @Override
  public void addTreasure(Treasure treasure) {
    this.treasure.add(treasure);
  }

  @Override
  public void addArrow() {
    this.arrows++;
  }

  @Override
  public void removeArrows() {
    this.arrows = 0;
  }

  @Override
  public int getArrows() {
    int arrows = this.arrows;
    return arrows;
  }

  @Override
  public void addMonster(Monster monster) {
    this.monster = monster;
  }

  //package private
  void removeMonster() {
    this.monster = null;
  }

  @Override
  public Monster getMonster() {
    Monster monster = this.monster;
    return monster;
  }

  @Override
  public void addPit() {
    this.pit = true;
  }

  @Override
  public boolean hasPit() {
    boolean hasPit = this.pit;
    return hasPit;
  }

  @Override
  public void addThief() {
    this.thief = true;
  }

  @Override
  public boolean hasThief() {
    boolean hasThief = this.thief;
    return hasThief;
  }

  @Override
  public boolean beenVisited() {
    boolean visited = this.visited;
    return visited;
  }

  @Override
  public void visit() {
    this.visited = true;
  }

  @Override
  public String toString() {
    String output = "(" + (xCoord - 1) + "," + (yCoord - 1) + ")";
    return output;
  }

}
