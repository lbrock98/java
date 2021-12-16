import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;

import dungeon.control.TextController;
import dungeon.control.TextControllerImpl;
import dungeon.model.Dungeon;
import dungeon.model.DungeonModel;

import static org.junit.Assert.assertEquals;

/**
 * Test for the TextControllerImpl class.
 */
public class TextControllerImplTest {
  private Dungeon model;
  private Dungeon dungeon;
  private StringBuilder gameLog;
  private StringReader input;

  @Before
  public void setUp() throws Exception {
    model = new DungeonModel(true, 7, 3, 2, 30, 4);
    dungeon = new DungeonModel(false, 5, 5, 0, 80,
            3);
    gameLog = new StringBuilder();
    input = new StringReader("m west east m north m east m east m east");

  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullInput() {
    TextController c = new TextControllerImpl(null, gameLog);
    c.game(model);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullOutput() {
    TextController c = new TextControllerImpl(input, null);
    c.game(model);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidModel() {
    Dungeon dungeon = new DungeonModel(false, 1, 1, 1, 12, 3);
    TextController c = new TextControllerImpl(input, gameLog);
    c.game(dungeon);
  }

  @Test(expected = IllegalStateException.class)
  public void testFailingAppendable() {
    // Testing when something goes wrong with the Appendable
    // Here we are passing it a mock of an Appendable that always fails
    Appendable gameLog = new FailingAppendable();
    TextController c = new TextControllerImpl(input, gameLog);
    c.game(model);
  }

  @Test
  public void testInvalidDirection() {
    TextController c = new TextControllerImpl(input, gameLog);
    c.game(dungeon);
    String[] lines = gameLog.toString().split("\n");
    String output = lines[9];
    assertEquals("This is not a valid direction, please try again: ", output);
  }

  @Test
  public void testExplanation() {
    TextController c = new TextControllerImpl(input, gameLog);
    c.game(dungeon);
    String[] lines = gameLog.toString().split("\n");
    String output = lines[4];
    assertEquals("Your options are to move on, pickup treasure and any arrows you " +
            "might find, or shoot.", output);
  }

  @Test
  public void testOptions() {
    TextController c = new TextControllerImpl(input, gameLog);
    c.game(dungeon);
    String[] lines = gameLog.toString().split("\n");
    String output = lines[6];
    assertEquals("Doors lead to: [EAST]", output);
    String outputTwo = lines[7];
    assertEquals("Move, Pickup, or Shoot (M-P-S)?", outputTwo);
    String chooseDirection = lines[8];
    assertEquals("Choose a direction to move in from the available doors:",
            chooseDirection);
  }

  @Test
  public void testInvalidInput() {
    StringReader input = new StringReader("fj m west east m north m east m east m east");
    TextController c = new TextControllerImpl(input, gameLog);
    c.game(dungeon);
    String[] lines = gameLog.toString().split("\n");
    String output = lines[8];
    assertEquals("This input is invalid. Please enter 'M', 'P', or 'S'.", output);
  }

  @Test
  public void testSmellMessage() {
    TextController c = new TextControllerImpl(input, gameLog);
    c.game(dungeon);
    String[] lines = gameLog.toString().split("\n");
    String output = lines[18];
    assertEquals("There is a WEAK smell of something terrible nearby...", output);
  }

  @Test
  public void testFoundMonster() {
    TextController c = new TextControllerImpl(input, gameLog);
    c.game(dungeon);
    String[] lines = gameLog.toString().split("\n");
    String output = lines[lines.length - 2];
    //System.out.println(lines.length - 2);
    assertEquals("There is a HEALTHY monster in this dungeon!", output);
    String outputTwo = lines[lines.length - 1];
    assertEquals("You have been slain! Game over. ", outputTwo);
  }

  @Test
  public void testKillMonster() {
    StringReader input = new StringReader("m west east m north s east 1 s east 1 m east m " +
            "east m east");
    TextController c = new TextControllerImpl(input, gameLog);
    c.game(dungeon);
    String[] lines = gameLog.toString().split("\n");
    String killedOtyugh = lines[lines.length - 4];
    assertEquals("This monster is already dead! Continue on. ", killedOtyugh);

    String wonGame = lines[lines.length - 3];
    assertEquals("You have successfully reached the end cave, slain the Otyugh, and won " +
            "the game!", wonGame);

    String shootMessage = lines[19];
    assertEquals("Please enter the direction in which you would like to shoot your " +
            "arrow: ", shootMessage);
    String directionMessage = lines[20];
    assertEquals("Please enter the distance that you would like to shoot your arrow: ",
            directionMessage);
    String shotOtyugh = lines[21];
    assertEquals("Congratulations! You have shot an Otyugh! ", shotOtyugh);
  }

  @Test
  public void testArrowErrors() {
    StringReader input = new StringReader("m west east m north s east one 1 s south 1 m east m " +
            "east m east");
    TextController c = new TextControllerImpl(input, gameLog);
    c.game(dungeon);
    String[] lines = gameLog.toString().split("\n");
    System.out.println(gameLog);
    String numberFormatException = lines[21];
    assertEquals("This input is invalid. Please enter your distance as a numeral. ",
            numberFormatException);
    String arrowMissed = lines[31];
    assertEquals("Your arrow has missed an Otyugh. Better luck next time. ",
            arrowMissed);
  }

  @Test
  public void testFindStuff() {
    StringReader input = new StringReader("p m east p m north m east m east m east");
    TextController c = new TextControllerImpl(input, gameLog);
    c.game(dungeon);
    System.out.println(gameLog);
    String[] lines = gameLog.toString().split("\n");
    String noTreasure = lines[8] + lines[9];
    assertEquals("This location has 0 diamond(s), 0 ruby(ies), and 0 sapphire(s). " +
            "Better luck next time!", noTreasure);
    String arrow = lines[10];
    assertEquals("This location also has an arrow that has been added to your quiver!",
            arrow);
    String treasure = lines[18] + lines[19];
    assertEquals("This location has 0 diamond(s), 0 ruby(ies), and 1 sapphire(s). " +
            "Hurray! This treasure is now yours!", treasure);
  }

}