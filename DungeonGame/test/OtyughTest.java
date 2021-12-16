import org.junit.Before;
import org.junit.Test;

import dungeon.model.Location;
import dungeon.model.LocationImpl;
import dungeon.model.Otyugh;

import static dungeon.model.Monster.Health.DEAD;
import static dungeon.model.Monster.Health.HEALTHY;
import static dungeon.model.Monster.Health.INJURED;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * JUnit test class for Otyugh class.
 */
public class OtyughTest {
  private Otyugh monster;

  @Before
  public void setUp() throws Exception {
    monster = new Otyugh();
  }

  @Test
  public void testLocation() {
    assertEquals(null, monster.getLocation());
    Location loc = new LocationImpl(3, 5);
    monster.setLocation(loc);
    assertEquals(loc, monster.getLocation());
  }

  @Test
  public void shot() {
    assertEquals(HEALTHY, monster.getHealth());
    monster.shot();
    assertEquals(INJURED, monster.getHealth());
    monster.shot();
    assertEquals(DEAD, monster.getHealth());
  }

  @Test
  public void testRoams() {
    assertFalse(monster.roams());
    monster.setRoams();
    assertTrue(monster.roams());
  }
}