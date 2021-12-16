import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;

import dungeon.model.Location;
import dungeon.model.LocationImpl;
import dungeon.model.Monster;
import dungeon.model.Otyugh;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;


/**
 * A JUnit test class for the LocationImpl class.
 */
public class LocationImplTest {
  private Location loc;

  @Before
  public void setUp() throws Exception {
    loc = new LocationImpl(3, 2);
  }

  //tests addMove and getMoves
  @Test
  public void testMoves() {
    HashSet<Location.Move> moves = new HashSet<Location.Move>();
    assertEquals(moves, loc.getMoves());
    loc.addMove(Location.Move.NORTH);
    loc.addMove(Location.Move.EAST);
    moves.add(Location.Move.NORTH);
    moves.add(Location.Move.EAST);
    assertEquals(moves, loc.getMoves());
  }

  //tests addTreasure, removeTreasure, and getTreasure
  @Test
  public void testTreasure() {
    ArrayList<Location.Treasure> treasures = new ArrayList<>();
    assertEquals(treasures, loc.getTreasure());
    loc.addTreasure(Location.Treasure.DIAMOND);
    loc.addTreasure(Location.Treasure.SAPPHIRE);
    treasures.add(Location.Treasure.DIAMOND);
    treasures.add(Location.Treasure.SAPPHIRE);
    assertEquals(treasures, loc.getTreasure());
    loc.removeTreasure();
    treasures = new ArrayList<>();
    assertEquals(treasures, loc.getTreasure());
  }

  @Test
  public void testToString() {
    assertEquals("(2,1)", loc.toString());
  }

  //tests addArrow, removeArrows, getArrows
  @Test
  public void testArrows() {
    assertEquals(0, loc.getArrows());
    loc.addArrow();
    loc.addArrow();
    assertEquals(2, loc.getArrows());
    loc.removeArrows();
    assertEquals(0, loc.getArrows());
  }

  //tests addMonster and getMonster
  @Test
  public void testMonster() {
    Monster monster = new Otyugh();
    loc.addMonster(monster);
    assertEquals(monster, loc.getMonster());
  }

  //tests addPit and hasPit
  @Test
  public void testPit() {
    assertFalse(loc.hasPit());
    loc.addPit();
    assertTrue(loc.hasPit());
  }

  //tests addThief and hasThief
  @Test
  public void testThief() {
    assertFalse(loc.hasThief());
    loc.addThief();
    assertTrue(loc.hasThief());
  }

  //tests beenVisited and visit
  @Test
  public void testVisited() {
    assertFalse(loc.beenVisited());
    loc.visit();
    assertTrue(loc.beenVisited());
  }

}