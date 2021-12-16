package dungeon.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;

import static dungeon.model.Location.Move.EAST;
import static dungeon.model.Location.Move.NORTH;
import static dungeon.model.Location.Move.SOUTH;
import static dungeon.model.Location.Move.WEST;

/**
 * This class represents a dungeon that is represented on a 2-D grid. It implements the Dungeon
 * interface. This dungeon takes a singular player. Locations in the dungeon are represented as an
 * (x,y) point. Each location has a path to every other location. Dungeons can either wrap (meaning
 * that if a player exits the dungeon to the North at x=2, then it will automatically reenter the
 * dungeon from the South at x=2), or not. A starting cave and ending cave are randomly selected,
 * ensuring that they have a distance of at least 5 from each other. Dungeons are constructed with a
 * degree of interconnectivity (interconnectivity = 0 means there is exactly one path from every
 * cave to every other cave in the dungeon).
 */

public class DungeonModel implements Dungeon {
  private Location[][] grid;
  private int treasurePercent;
  private Player player;
  private Location start;
  private Location end;
  private ArrayList<Location> caves;
  private ArrayList<Location> tunnels;
  private int monsters;
  private ArrayList<Location> monsterCaves;
  private Monster roamingMonster;
  private final int width;
  private final int height;
  private ArrayList<Location> locs;
  private final int intercon;
  private Location pit;

  /**
   * Constructor.
   *
   * @throws IllegalArgumentException    if x or y dimensions are >100 or if the product of x and y
   *      are <6, or if treasurePercent is <0 or >100, or if the distance between the random start
   *      and end caves is <5, or if the interconnectivity is too big for the given dungeon size or
   *      is negative, or if the number of monsters given is greater than the number of caves in
   *      the dungeon
   */
  public DungeonModel(boolean wraps, int x, int y, int intercon, int percent, int monsters) {
    //throw exceptions
    if ((x > 100) || (y > 100)) {
      throw new IllegalArgumentException("Parameters are too large! ");
    }
    if (x * y < 6) {
      throw new IllegalArgumentException("Parameters are too small!");
    }
    if (percent < 0 || percent > 100) {
      throw new IllegalArgumentException("Invalid percentage value! ");
    }
    if (intercon > ((x * y) / 2)) {
      throw new IllegalArgumentException("Interconnectivity is too big for the given dungeon " +
              "size! ");
    }
    if (intercon < 0) {
      throw new IllegalArgumentException("Interconnectivity cannot be negative! ");
    }
    //System.out.println("got past arguments");
    //initialize variables
    this.intercon = intercon;
    this.width = x;
    this.height = y;
    this.grid = new Location[x][y];
    this.treasurePercent = percent;
    this.player = new PlayerImpl();
    this.caves = new ArrayList<Location>();
    this.tunnels = new ArrayList<Location>();
    this.monsters = monsters;
    this.monsterCaves = new ArrayList<Location>();
    this.locs = new ArrayList<Location>();
    //System.out.println("variables initialized");
    //create locations, sets for each location, and edges
    int temp = y;
    List<List<Location>> locations = new ArrayList<>();
    for (int i = 0; i < x * y; i++) {
      locations.add(new ArrayList());
    }
    ArrayList<Edge> edges = new ArrayList<Edge>();
    int count = x * y;
    while (x > 0) {
      y = temp;
      while (y > 0) {
        LocationImpl mine = new LocationImpl(x, y);
        locs.add(mine);
        grid[x - 1][y - 1] = mine;
        if (x == 1) {
          if (y != 1) {
            edges.add(new Edge(x, y, x, y - 1)); //down
            if (wraps) {
              if (y == grid[0].length) {
                edges.add(new Edge(x, y, x, 1)); //up
              }
            }
          }
        }
        else {
          if (y != 1) {
            edges.add(new Edge(x, y, x, y - 1)); //down
            edges.add(new Edge(x, y, x - 1, y)); //left
            if (wraps) {
              if (y == grid[0].length) {
                edges.add(new Edge(x, y, x, 1)); //up
              }
              if (x == grid.length) {
                edges.add(new Edge(x, y, 1, y));//right
              }
            }
          }
          else {
            edges.add(new Edge(x, y, x - 1, y)); //left
            if (wraps) {
              if (x == grid.length) {
                edges.add(new Edge(x, y, 1, y));//right
              }
            }
          }
        }
        locations.get(count - 1).add(mine);
        count--;
        y--;
      }
      x--;
    }
    //System.out.println("locations and edges created");
    //choose edges at random and put the locations in the same set
    ArrayList<Edge> leftovers = new ArrayList<>();
    ArrayList<Edge> usedEdges = new ArrayList<>();
    while (locations.size() > 1) {
      //select random edge
      Random randomizer = new Random();
      int random = randomizer.nextInt(edges.size());
      Edge edge = edges.get(random);
      //get the two locations connecting the edge
      Location locOne = grid[edge.getVertices()[0] - 1][edge.getVertices()[1] - 1];
      Location locTwo = grid[edge.getVertices()[2] - 1][edge.getVertices()[3] - 1];
      //get the index of the sets of the two locations
      int indexFirst = getIndex(locOne, locations)[0];
      int indexSecond = getIndex(locTwo, locations)[0];
      //if they're not in the same set, add the second loc to the first loc's set
      //remove the second loc
      //add the current edge to used Edges
      if (indexFirst != indexSecond) {
        for (Location location : locations.get(indexSecond)) {
          locations.get(indexFirst).add(location);
        }
        locations.remove(indexSecond);
        usedEdges.add(edges.get(random));
      }
      //if they are in the same set, then add the edge to leftovers
      else if (indexFirst == indexSecond) {
        leftovers.add(edges.get(random));
      }
      //remove the edge from edges
      edges.remove(random);
    }
    //add more edges for given interconnectivity
    for (int i = 0; i < intercon; i++) {
      try {
        usedEdges.add(leftovers.get(i));
      }
      catch (IndexOutOfBoundsException iobe) {
        throw new IllegalArgumentException("Interconnectivity is too big for the given dungeon " +
        "size!");
      }
    }
    //System.out.println("edges added");
    //loop through edges and add moves to locations, also add to a double list of Locations to show
    //starting and ending location
    for (int i = 0; i < usedEdges.size(); i++) {
      Edge edge = usedEdges.get(i);
      int xOne = edge.getVertices()[0] - 1;
      int yOne = edge.getVertices()[1] - 1;
      int xTwo = edge.getVertices()[2] - 1;
      int yTwo = edge.getVertices()[3] - 1;
      LocationImpl one = (LocationImpl) grid[xOne][yOne];
      LocationImpl two = (LocationImpl) grid[xTwo][yTwo];
      //this.edges.get(one.getId()).add(two.getId());
      //this.edges.get(two.getId()).add(one.getId());
      if (xOne == xTwo + 1 || xOne < xTwo - 1) {
        one.addMove(WEST);
        two.addMove(EAST);
      }
      else if (xOne == xTwo - 1 || xOne > xTwo + 1) {
        one.addMove(EAST);
        two.addMove(WEST);
      }
      else if (yOne == yTwo + 1 || yOne < yTwo - 1) {
        one.addMove(SOUTH);
        two.addMove(NORTH);
      }
      else if (yOne == yTwo - 1 || yOne > yTwo + 1) {
        one.addMove(NORTH);
        two.addMove(SOUTH);
      }
    }
    // System.out.println("moves added");
    //give values to start location, end location, and caves, give locations their id
    setLocs();
    setStart();
    setEnd();
    // System.out.println("set start, end, locs");
    setMonsters(intercon);
    //System.out.println("set monsters");
    if (monsters > caves.size() - 3) {
      throw new IllegalArgumentException("That many monsters can't fit in a dungeon of this size!");
    }
    setObstacles();
    //System.out.println("set obstacles");
    //add treasure and arrows
    addTreasure();
    addArrows();
    //System.out.println("private methods called");
  }

  //private methods used in the constructor
  private void setStart() {
    Random randomizer = new Random();
    while (true) {
      int random = randomizer.nextInt(this.caves.size());
      Location loc = this.caves.get(random);
      if (loc.getMoves().size() != 2) {
        this.player.move(loc);
        this.start = loc;
        //System.out.println("got start!");
        break;
      }
      //System.out.println("can't get start");
    }
  }

  private void setEnd() {
    Random randomizer = new Random();
    int check = 0;
    while (true) {
      int random = randomizer.nextInt(this.caves.size());
      Location loc = this.caves.get(random);
      if (getPath(this.start, loc).size() > 5) {
        this.end = loc;
        //System.out.println("!!!!!!!!!!END: " + loc.toString());
        break;
      }
      check++;
      if (check > 10) {
        throw new IllegalArgumentException("Parameters are too small!");
      }
      //System.out.println("can't get end");
    }
  }

  private void setLocs() {
    for (Location loc : locs) {
      int moves = loc.getMoves().size();
      if (moves != 2) {
        this.caves.add(loc);
      }
      else {
        this.tunnels.add(loc);
      }
    }
  }

  private void setMonsters(int intercon) {
    int monsters = this.monsters;
    Monster monster = new Otyugh();
    if (!this.caves.contains(this.end)) {
      throw new IllegalStateException("Shouldn't happen!!!!!");
    }
    this.end.addMonster(monster);
    this.monsterCaves.add(this.end);
    monster.setLocation(this.end);
    ArrayList<Location> caves = this.caves;
    //System.out.println("CAVES: " + this.caves.toString());
    while (monsters > 1) {
      monster = new Otyugh();
      Random randomizer = new Random();
      int rand = randomizer.nextInt(caves.size());
      if (!caves.get(rand).equals(this.start) && caves.get(rand).getMonster() == null) {
        if (monsters == this.monsters && intercon > 1) {
          monster.setRoams();
          this.roamingMonster = monster;
          //System.out.println("roaming monster set");
        }
        this.monsterCaves.add(caves.get(rand));
        caves.get(rand).addMonster(monster);
        monster.setLocation(caves.get(rand));
        //System.out.println("MONSTER ADDED TO: " + caves.get(rand));
        monsters--;
      }
    }
  }

  //adds pit and thief
  private void setObstacles() {
    int count = 0;
    while (true) {
      Random randomizer = new Random();
      ArrayList<Location> noMonsters = new ArrayList<>();
      noMonsters.addAll(this.caves);
      noMonsters.removeAll(this.monsterCaves);
      //System.out.println("caves: " + this.caves.toString() + "\nmonster caves: " +
      //        this.monsterCaves.toString() + "\nno monster caves: " + noMonsters.toString());
      int rand = randomizer.nextInt(noMonsters.size());
      Location loc = noMonsters.get(rand);
      if (getPath(this.start, loc).size() < 2 || getPath(this.end, loc).size() < 2 ||
              this.end == loc) {
        continue;
      }
      else {
        if (count == 0) {
          this.caves.get(rand).addPit();
          this.pit = this.caves.get(rand);
          //System.out.println("PIT ADDED TO: " + this.caves.get(rand).toString());
          count++;
        }
        else {
          this.caves.get(rand).addThief();
          break;
        }
      }
    }
  }

  private void addTreasure() {
    ArrayList<Location> caves = this.caves;
    int someCaves = (int) Math.ceil(caves.size() * treasurePercent / 100.0);
    for (int i = 0; i < someCaves; i++) {
      Location.Treasure[] treasures = Location.Treasure.values();
      Random randomizer = new Random();
      int random = randomizer.nextInt(treasures.length);
      Location.Treasure treasure = treasures[random];
      int ran = randomizer.nextInt(caves.size());
      caves.get(ran).addTreasure(treasure);
      caves.remove(ran);
    }
  }

  private void addArrows() {
    int someLocs = (int) Math.ceil(this.locs.size() * treasurePercent / 100.0);
    for (int i = 0; i < someLocs; i++) {
      Random randomizer = new Random();
      int rand = randomizer.nextInt(this.locs.size());
      this.locs.get(rand).addArrow();
      this.locs.remove(rand);
    }
  }

  //other private helper methods
  private int[] getIndex(Location loc, List<List<Location>> locations) {
    int[] index = new int[2];
    for ( int i = 0; i < locations.size(); ++i ) {
      for ( int j = 0; j < locations.get(i).size(); ++j ) {
        if (locations.get(i).get(j) == loc) {
          index[0] = i;
          index[1] = j;
        }
      }
    }
    return index;
  }

  private ArrayList<Location> bfs(Location one, Location two) {
    Queue<Location> frontier = new LinkedList<>();
    ArrayList<Location> explored = new ArrayList<>();
    frontier.add(one);
    //System.out.println("first added to frontier: " + one.toString());
    while (frontier.size() > 0) {
      Location node = frontier.remove();
      Set<Location.Move> moves = node.getMoves();
      explored.add(node);
      if (node.toString().compareToIgnoreCase(two.toString()) == 0) {
        return explored;
      }
      for (Location.Move move : moves) {
        Location successor = newLoc(node, move);
        if (!explored.contains(successor)) {
          frontier.add(successor);
          //System.out.println("successor added to frontier: " + successor.toString());
        }
      }
    }
    return explored;
  }

  private ArrayList<Location> dfs(Location one, Location two) {
    Stack<Location> frontier = new Stack<>();
    ArrayList<Location> visited = new ArrayList<>();
    frontier.push(one);
    while (!frontier.empty()) {
      Location node = frontier.pop();
      Set<Location.Move> moves = node.getMoves();
      visited.add(node);
      if (node.equals(two)) {
        return visited;
      }
      for (Location.Move move : moves) {
        Location successor = newLoc(node, move);
        if (!visited.contains(successor)) {
          frontier.push(successor);
        }
      }
    }
    return visited;
  }

  //public interface methods
  @Override
  public void cheat() {
    int num = 0;
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[0].length; j++) {
        Location loc = grid[i][j];
        loc.visit();
        num++;
        if (loc == null) {
          throw new IllegalStateException("No locations should be null!");
        }
      }
    }
  }

  @Override
  public boolean arrowShot(Location.Move direction, int distance) {
    this.player.removeArrow();
    Location loc = this.player.getLocation();
    while (distance > 0) {
      loc = newLoc(loc, direction);
      if (this.tunnels.contains(loc)) {
        Set<Location.Move> moves = loc.getMoves();
        switch (direction.name()) {
          case "NORTH":
            moves.remove(SOUTH);
            break;
          case "SOUTH":
            moves.remove(NORTH);
            break;
          case "EAST":
            moves.remove(WEST);
            break;
          case "WEST":
            moves.remove(EAST);
            break;
          default:
            throw new IllegalStateException("direction.name() should be one of NORTH, SOUTH, " +
                    "EAST, WEST. ");
        }
        if (moves.size() > 1) {
          throw new IllegalStateException("A tunnel shouldn't have more than two moves");
        }
        direction = moves.iterator().next();
      } else {
        distance--;
      }
    }
    if (loc.getMonster() != null && !loc.getMonster().getHealth().name().equals("DEAD")) {
      loc.getMonster().shot();
      return true;
    }
    return false;
  }

  @Override
  public Smell findMonsters() {
    Location playerLoc = this.player.getLocation();
    int counter = 0;
    for (Location loc : this.monsterCaves) {
      int average = (int) Math.floor((this.grid.length + this.grid[0].length) / 4);
      int path = getPath(playerLoc, loc).size();
      int distance = (int) Math.floor(path / average);
      Monster monster = loc.getMonster();
      if (distance <= 1 && monster.getHealth() != Monster.Health.DEAD) {
        return Smell.STRONG;
      }
      else if (distance == 2 && monster.getHealth() != Monster.Health.DEAD) {
        counter++;
      }
    }
    if (counter >= 2) {
      return Smell.STRONG;
    }
    else if (counter >= 1) {
      return Smell.WEAK;
    }
    return Smell.NONE;
  }

  @Override
  public Howl findPit() {
    Location pitLoc = this.pit;
    if (pitLoc == null) {
      throw new IllegalStateException("pit's location shouldn't be null!!!");
    }
    int average = (int) Math.floor((this.grid.length + this.grid[0].length) / 4);
    int path = getPath(pitLoc, this.player.getLocation()).size();
    int distance = (int) Math.floor(path / average);
    if (distance <= 1) {
      return Howl.LOUD;
    }
    else if (distance <= 4) {
      return Howl.FAINT;
    }
    return Howl.NONE;
  }

  @Override
  public Location getStart() {
    Location start = this.start;
    return start;
  }

  @Override
  public Location getEnd() {
    Location end = this.end;
    return end;
  }

  @Override
  public Player getPlayer() {
    Player player = this.player;
    return player;
  }

  @Override
  public int[] getDimensions() {
    int[] dimensions = new int[2];
    dimensions[0] = this.width;
    dimensions[1] = this.height;
    return dimensions;
  }

  @Override
  public String printDungeon() {
    int x = grid.length;
    int y = grid[0].length;
    String dungeon = "";
    for (int i = y - 1; i >= 0; i--) {
      int r = 0;
      while (r < 3) {
        for (int j = 0; j < x; j++) {
          Set<Location.Move> moves = grid[j][i].getMoves();
          int moveSize = moves.size();
          switch (r) {
            case 0:
              dungeon = dungeon + " ";
              if (moves.contains(Location.Move.NORTH)) {
                dungeon = dungeon + "|";
              } else {
                dungeon = dungeon + " ";
              }
              dungeon = dungeon + " ";
              break;
            case 1:
              if (moves.contains(Location.Move.WEST)) {
                dungeon = dungeon + "-";
              } else {
                dungeon = dungeon + " ";
              }
              if (moveSize == 2) {
                dungeon = dungeon + "+";
              } else if (grid[j][i].getMonster() != null) {
                dungeon = dungeon + "m";
              } else if (grid[j][i].getTreasure().size() > 0) {
                dungeon = dungeon + "*";
              } else {
                dungeon = dungeon + "o";
              }
              if (moves.contains(Location.Move.EAST)) {
                dungeon = dungeon + "-";
              } else {
                dungeon = dungeon + " ";
              }
              break;
            case 2:
              dungeon = dungeon + " ";
              if (moves.contains(Location.Move.SOUTH)) {
                dungeon = dungeon + "|";
              } else {
                dungeon = dungeon + " ";
              }
              dungeon = dungeon + " ";
              break;
            default:
              dungeon = "error";
              break;
          }
        }
        r++;
        dungeon = dungeon + "\n";
      }
    }
    return dungeon;
  }


  @Override
  public ArrayList<Location> getPath(Location one, Location two) {
    if (one == two) {
      return new ArrayList<Location>();
    }
    ArrayList<Location> bfs = bfs(one, two);
    ArrayList<Location> dfs = dfs(one, two);
    if (bfs.size() < dfs.size()) {
      return bfs;
    }
    else {
      return dfs;
    }
  }

  @Override
  public Location getLocation(int x, int y) {
    Location loc = grid[x][y];
    return loc;
  }

  @Override
  public String description(Object o) {
    Location loc = new LocationImpl(0, 0);
    Player player = new PlayerImpl();
    String name = "";
    String moves = "";
    int arrow = 0;
    List<Location.Treasure> treasure = new ArrayList<>();
    if (o instanceof Location) {
      loc = (Location) o;
      treasure = loc.getTreasure();
      name = "This location has ";
      moves = loc.getMoves().toString();
      moves = moves.substring( 1, moves.length() - 1 );
      moves = "There are doors leading to the: " + moves + ".\n";
      arrow = loc.getArrows();
    }
    else if (o instanceof Player) {
      player = (Player) o;
      treasure = player.getTreasure();
      name = "You have ";
      arrow = player.getArrows();
    }
    int diamond = 0;
    int ruby = 0;
    int sapphire = 0;
    for (Location.Treasure mine : treasure) {
      switch (mine.name()) {
        case ("DIAMOND"):
          diamond++;
          break;
        case ("RUBY"):
          ruby++;
          break;
        case ("SAPPHIRE"):
          sapphire++;
          break;
        default:
          break;
      }
    }
    String output = moves + name + diamond + " diamond(s), " + ruby + " ruby(ies), " + sapphire
            + " sapphire(s), and " + arrow + " arrow(s)." ;
    return output;
  }

  @Override
  public Location newLoc(Location location, Location.Move move) {
    Set<Location.Move> moves = location.getMoves();
    if (!moves.contains(move)) {
      throw new IllegalArgumentException("This is not a valid move for this location.");
    }
    List<List<Location>> allLocs =
            Arrays.stream(this.grid).map(Arrays::asList).collect(Collectors.toList());
    int oneX = getIndex(location, allLocs)[0];
    int oneY = getIndex(location, allLocs)[1];
    int nextX = oneX;
    int nextY = oneY;
    switch (move.name()) {
      case "SOUTH":
        if (oneY == 0) {
          nextY = grid[0].length - 1;
        }
        else {
          nextY--;
        }
        break;
      case "NORTH":
        if (oneY == grid[0].length - 1) {
          nextY = 0;
        }
        else {
          nextY++;
        }
        break;
      case "WEST":
        if (oneX == 0) {
          nextX = grid.length - 1;
        }
        else {
          nextX--;
        }
        break;
      case "EAST":
        if (oneX == grid.length - 1) {
          nextX = 0;
        }
        else {
          nextX++;
        }
        break;
      default:
        break;
    }
    Location newLoc = getLocation(nextX, nextY);
    return newLoc;
  }

  @Override
  public void monsterRoams() {
    if (this.intercon > 1) {
      if (!this.roamingMonster.roams()) {
        throw new IllegalStateException("The roaming monster must roam. ");
      }
      LocationImpl oldLoc = (LocationImpl) this.roamingMonster.getLocation();
      Set<Location.Move> moves = oldLoc.getMoves();
      Location.Move[] arrayMoves = moves.toArray(new Location.Move[moves.size()]);
      Random randomizer = new Random();
      int i = 0;
      //System.out.println("current loc: " + oldLoc.toString());
      //System.out.println(moves);
      while (i < moves.size() * 3) {
        int random = randomizer.nextInt(arrayMoves.length);
        Location.Move randomMove = arrayMoves[random];
        Location newLoc = newLoc(oldLoc, randomMove);
        if (newLoc.getMonster() == null) {
          this.roamingMonster.setLocation(newLoc);
          newLoc.addMonster(this.roamingMonster);
          oldLoc.removeMonster();
          this.monsterCaves.add(newLoc);
          this.monsterCaves.remove(oldLoc);
          //System.out.println("moving roaming monster to " + newLoc.toString());
          //System.out.println("monster: " + newLoc.getMonster());
          break;
        }
        i++;
      }
    }
  }
}