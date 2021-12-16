import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;

import dungeon.model.Location;
import dungeon.model.LocationImpl;
import dungeon.model.Monster;
import dungeon.model.Otyugh;
import dungeon.model.PlayerImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * A JUnit test class for the PlayerImpl class.
 */
public class PlayerImplTest {
  private PlayerImpl player;
  private LocationImpl loc;

  @Before
  public void setUp() {
    player = new PlayerImpl();
    loc = new LocationImpl(3,5);
  }

  //tests move and getLocation
  @Test
  public void testLocation() {
    assertEquals(null, player.getLocation());
    player.move(loc);
    assertEquals(loc, player.getLocation());
  }

  //tests collectTreasure and getTreasure, and encountering a thief
  @Test
  public void testTreasure() {
    ArrayList<Location.Treasure> treasure = new ArrayList<Location.Treasure>();
    assertEquals(treasure, player.getTreasure());
    loc.addTreasure(Location.Treasure.DIAMOND);
    loc.addTreasure(Location.Treasure.RUBY);
    player.move(loc);
    player.collectTreasure();
    treasure.add(Location.Treasure.DIAMOND);
    treasure.add(Location.Treasure.RUBY);
    assertEquals(treasure, player.getTreasure());
    int treasureNum = player.getTreasure().size();
    Location newLoc = new LocationImpl(3,4);
    newLoc.addThief();
    player.move(newLoc);
    assertEquals(treasureNum - 1, player.getTreasure().size());
  }

  //tests collectArrow and getArrows
  @Test
  public void testArrow() {
    assertEquals(3, player.getArrows());
    loc.addArrow();
    player.move(loc);
    player.collectArrows();
    assertEquals(4, player.getArrows());
    player.removeArrow();
    assertEquals(3, player.getArrows());
  }

  @Test
  public void testEaten() {
    assertEquals(true, player.getHealth());
    Monster monster = new Otyugh();
    loc.addMonster(monster);
    player.move(loc);
    assertEquals(false, player.getHealth());
  }

  @Test
  public void testMaybeEaten() {
    Monster monster = new Otyugh();
    loc.addMonster(monster);
    monster.shot();
    player.move(loc);
    assertEquals(true, player.getHealth());
    //assertEquals(false, player.getHealth()); //works when random is seeded with 1
  }

  @Test
  public void testNotEaten() {
    Monster monster = new Otyugh();
    loc.addMonster(monster);
    monster.shot();
    monster.shot();
    player.move(loc);
    assertEquals(true, player.getHealth());
  }

  @Test
  public void testPit() {
    assertTrue(player.getHealth());
    loc.addPit();
    player.move(loc);
    assertFalse(player.getHealth());
  }

}