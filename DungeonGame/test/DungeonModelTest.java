import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import dungeon.model.Dungeon;
import dungeon.model.DungeonModel;
import dungeon.model.Location;
import dungeon.model.Monster;
import dungeon.model.Otyugh;
import dungeon.model.Player;

import static dungeon.model.Dungeon.Smell.NONE;
import static dungeon.model.Dungeon.Smell.STRONG;
import static dungeon.model.Dungeon.Smell.WEAK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * A JUnit test class for the DungeonModel class.
 */
public class DungeonModelTest {
  private DungeonModel dungeon;
  private DungeonModel dungeonTwo;


  @Before
  public void setUp() {
    dungeon = new DungeonModel(false, 5, 5, 0, 20, 3);
    dungeonTwo = new DungeonModel(true, 8, 4, 0, 75, 1);
  }

  //MANY OF THESE TESTS WILL SOMETIMES FAIL BECAUSE THE RANDOM INPUTS AREN'T CURRENTLY SEEDED

  @Test(expected = IllegalArgumentException.class)
  public void testConstruction1() {
    DungeonModel dungeon = new DungeonModel(true, 5, 5, 2, -1, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstruction2() {
    DungeonModel dungeon = new DungeonModel(true, 105, 5, 2, 10, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstruction3() {
    DungeonModel dungeon = new DungeonModel(true, 1, 5, 2, 10, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstruction4() {
    DungeonModel dungeon = new DungeonModel(true, 5, 5, 20, -1, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstruction5() {
    DungeonModel dungeon = new DungeonModel(true, 5, 5, -2, 10, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstruction6() {
    DungeonModel dungeon = new DungeonModel(true, 5, 5, 10, -1, 1);
  }

  @Test
  public void addTreasure() {
    ArrayList<Location> locations = new ArrayList<>();
    ArrayList<Location> locWithTreasure = new ArrayList<>();
    for (int i = 0; i > 5; i++) {
      for (int j = 0; j > 5; j++) {
        Location loc = dungeon.getLocation(i, j);
        if (loc.getMoves().size() != 2) {
          locations.add(loc);
          if (loc.getTreasure().size() > 0) {
            locWithTreasure.add(loc);
          }
        }
      }
    }
    assertTrue(locWithTreasure.size() >= locations.size() * .2);
  }

  @Test
  public void testGetStart() {
    assertEquals("(0,3)", dungeon.getStart().toString());
  }

  @Test
  public void testGetEnd() {
    assertEquals("(4,4)", dungeon.getEnd().toString());
  }

  @Test
  public void testPathLength() {
    assertTrue(dungeon.getPath(dungeon.getStart(), dungeon.getEnd()).size() > 5);
  }

  @Test
  public void testPrintDungeon() {
    //assertEquals("", dungeon.printDungeon());
    assertEquals("               \n m--*--+--+--m \n    |          \n    |          \n" +
            " o--o--*--o--o \n    |  |  |    \n    |  |  |    \n o--o  +  *--+ \n    |  |  |  | " +
            "\n    |  |  |  | \n o--o  o  o  + \n    |        | \n    |        | \n o--m--+--o  o" +
            " \n               \n", dungeon.printDungeon());


    assertEquals(" |    \n *--m \n |    \n |    \n +--* \n      \n      \n +--* \n |    " +
            "\n |    \n o--* \n |    \n", dungeonTwo.printDungeon());
  }


  @Test
  public void testGetPath() {
    Location start = dungeon.getLocation(0, 3);
    Location start1 = dungeon.getLocation(0,3);
    assertEquals(0, dungeon.getPath(start, start1).size());
    Location end = dungeon.getLocation(2, 3);
    assertEquals("[(0,3), (1,3), (2,3)]", dungeon.getPath(start, end).toString());
    Location one = dungeon.getLocation(0,0);
    Location two = dungeon.getLocation(3, 4);
    assertEquals("[(0,0), (1,0), (1,1), (1,2), (1,3), (1,4), (0,4), (2,4), (3,4)]",
            dungeon.getPath(one, two).toString());
    Location three = dungeonTwo.getLocation(0,1);
    Location four = dungeonTwo.getLocation(0, 3);
    assertEquals("[(0,1), (0,0), (0,3)]",
            dungeonTwo.getPath(three, four).toString());
  }

  @Test
  public void testGetLocation() {
    Location loc = dungeon.getLocation(0, 1);
    assertEquals("(0,1)", loc.toString());
  }

  //tests getDescription, getLocation, and getPlayer
  @Test
  public void testGetDescription() {
    Location loc = dungeon.getLocation(1,2);
    assertEquals("There are doors leading to the: WEST\nThis location has 0 diamond(s)," +
            " 0 ruby(ies), 0 sapphire(s), and 0 arrow(s).", dungeon.description(loc));
    Player player = dungeon.getPlayer();
    assertEquals("You have 0 diamond(s), 0 ruby(ies), 0 sapphire(s), and 3 arrow(s).",
            dungeon.description(player));
    System.out.println(loc.getTreasure().toString());
  }

  @Test
  public void testNewLoc() {
    Location loc = dungeon.getLocation(1,2);
    Location loc2 = dungeon.getLocation(0, 2);
    assertEquals(loc2, dungeon.newLoc(loc, Location.Move.WEST));
    Location loc3 = dungeon.getLocation(0,3);
    System.out.println(loc3.getMoves());
  }

  @Test
  public void testArrowShot() {
    Player player = dungeon.getPlayer();
    Location loc = dungeon.getLocation(1, 4);
    player.move(loc);
    System.out.println(dungeon.getEnd().getMonster().getHealth().name());
    assertEquals(true, dungeon.arrowShot(Location.Move.EAST, 1));
    assertEquals(false, dungeon.arrowShot(Location.Move.SOUTH, 2));
    assertEquals(true, dungeon.arrowShot(Location.Move.SOUTH, 4));

    Location loc2 = dungeon.getLocation(3, 2);
    Monster monster = new Otyugh();
    loc2.addMonster(monster);
    Location loc3 = dungeon.getLocation(4, 0);
    player.move(loc3);
    assertEquals(true, dungeon.arrowShot(Location.Move.NORTH, 1));
  }

  @Test
  public void testFindMonsters() {
    Player player = dungeon.getPlayer();
    System.out.println(player.getLocation().toString());
    assertEquals(STRONG, dungeon.findMonsters());
    dungeon.arrowShot(Location.Move.EAST, 2);
    dungeon.arrowShot(Location.Move.EAST, 2);
    Location loc = dungeon.getLocation(1, 2);
    player.move(loc);
    assertEquals(WEAK, dungeon.findMonsters());
    Location locTwo = dungeon.getLocation(3, 4);
    player.move(locTwo);
    assertEquals(NONE, dungeon.findMonsters());
  }

  @Test
  public void testSetMonsters() {
    assertEquals(null, dungeon.getStart().getMonster());
    assertNotEquals(null, dungeon.getEnd().getMonster());
    int counter = 0;
    for (int i = 0; i < 5; i++) {
      for (int j = 0; j < 5; j++) {
        Location loc = dungeon.getLocation(i, j);
        if (loc.getMoves().size() == 2) {
          if (loc.getMonster() != null) {
            counter++;
          }
        }
      }
    }
    assertEquals(0, counter);
  }

  //tests cheat and getDimensions()
  @Test
  public void cheat() {
    int width = dungeonTwo.getDimensions()[0];
    int height = dungeonTwo.getDimensions()[1];
    assertEquals(8, width);
    assertEquals(4, height);
    int count = 0;
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        Location loc = dungeonTwo.getLocation(i, j);
        if (loc.beenVisited()) {
          count++;
        }
      }
    }
    assertEquals(1, count); //one is for the player starting at the start location
    count = 0;
    dungeonTwo.cheat();
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        Location loc = dungeonTwo.getLocation(i, j);
        if (loc.beenVisited()) {
          count++;
        }
      }
    }
    assertEquals(width * height , count);
  }

  @Test
  public void testFindPit() {
    //fails because random inputs aren't seeded for game
    Player player = dungeon.getPlayer();
    Location loc = dungeon.getLocation(1, 2);
    player.move(loc);
    assertEquals(Dungeon.Howl.FAINT, dungeon.findPit());
    Location locTwo = dungeon.getLocation(3, 4);
    player.move(locTwo);
    assertEquals(Dungeon.Howl.LOUD, dungeon.findPit());
  }

  @Test
  public void testMonsterRoams() {
    Dungeon dungeon = new DungeonModel(false, 10, 4, 3, 80, 3);
    String first = dungeon.printDungeon();
    //System.out.println(dungeon.printDungeon());
    dungeon.monsterRoams();
    assertNotEquals(first, dungeon.printDungeon());
    //System.out.println(dungeon.printDungeon());
  }

}