package dungeon.control;

import javax.swing.JMenuItem;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.util.Arrays;

import dungeon.model.Dungeon;
import dungeon.model.DungeonModel;
import dungeon.model.Location;
import dungeon.model.Monster;
import dungeon.model.Player;
import dungeon.view.PrelaunchView;
import dungeon.view.View;
import dungeon.view.ViewImpl;

/**
 * This class implements the GraphicController interface, and controls the graphical dungeon game,
 * using a dungeon model.
 */
public class GraphicControllerImpl implements GraphicController, ActionListener, KeyListener {
  private PrelaunchView prelaunchView;
  private View view;
  private Dungeon model;
  private Player player;
  private boolean directionGiven;

  /**
   * Constructor for the graphical controller.
   *
   * @param prelaunchView   the prelaunch view, which will get me my model and normal view
   */
  public GraphicControllerImpl(PrelaunchView prelaunchView) {
    this.prelaunchView = prelaunchView;
    this.view = null;
    this.model = null;
    this.player = null;
    this.directionGiven = false;
  }

  @Override
  public void setUp() {
    this.prelaunchView.setCommandButtonListener(this);
    this.prelaunchView.makeVisible();
  }

  @Override
  public void game(Dungeon model) {
    this.model = model;
    if (this.model == null) {
      throw new IllegalStateException("Model cannot be null when game is called");
    }
    this.player = model.getPlayer();
    this.view = new ViewImpl(model);
    view.addListeners(this, this, this);
    view.makeVisible();
    prelaunchView.close();
  }

  @Override
  public void keyTyped(KeyEvent e) {
    Character key = e.getKeyChar();
    System.out.println("KEY TYPED: " + key);
    if (key.equals('m') || key.equals('s')) {
      this.directionGiven = false;
    }
    String instructions = "";
    if (key.equals('c')) {
      System.out.println("cheat!");
      this.model.cheat();
      instructions = "<html><div style='text-align:center'>Move, Pickup, or Shoot: (m-p-s)" +
              "</div></html>";
    }
    else if (key.equals('p')) {
      System.out.println("pickup!");
      this.player.collectTreasure();
      this.player.collectArrows();
      instructions = "<html><div style='text-align:center'>This location's items " +
              "have now been added to your possessions!<br>Move, Pickup, or Shoot: (m-p-s)" +
              "</div></html>";
    }
    Character last = view.lastKey();
    if (last != null) {
      if (last.equals('s') && this.directionGiven) {
        try {
          int num = Integer.valueOf(Character.toString(key));
          System.out.println("trying to shoot " + this.view.getDirection());
          boolean shot = this.model.arrowShot(this.view.getDirection(), num);
          if (shot) {
            instructions = "<html><div style='text-align:center'>You have shot a monster!";
          }
          else {
            instructions = "<html><div style='text-align:center'>Your arrow has missed!";
          }
          instructions += "<br>Move, Pickup, or Shoot: (m-p-s)</div></html>";
        }
        catch (NumberFormatException nfe) {
          System.out.println("key pressed was not a number");
        }
        catch (IllegalArgumentException iae) {
          this.view.showErrorMessage(iae.getMessage());
        }
      }
    }
    if (!instructions.equals("")) {
      this.view.setInstructions(instructions);
    }
    this.view.refresh();
  }

  private String move(Location.Move move) {
    System.out.println("PLAYER IS SUPPOSED TO MOVE");
    this.model.monsterRoams();
    Location curr = player.getLocation();
    System.out.println(move.name() + ", " + curr.toString());
    String instructions = "";
    try {
      Location next = this.model.newLoc(curr, move);
      player.move(next);
      Monster monster = next.getMonster();
      if (next.hasPit()) {
        instructions = "You've fallen into the wolf pit and died! Game over!";
        gameOver();
      } else if (next.hasThief()) {
        instructions = "Uh oh, you've encountered a thief who's stolen some of your treasure!";
      } else if (monster != null) {
        Monster.Health monsterHealth = monster.getHealth();
        instructions = "Uh oh, you've encountered a " + monsterHealth.name() + " monster!<br>";
        if (player.getHealth()) {
          if (this.model.getEnd() == next) {
            instructions += "CONGRATULATIONS! You've slain the monster, reached the end location," +
                    " and won the game!";
            gameOver();
          } else {
            instructions += "Hurrah! You've slain the monster! Continue on!<br>Move, Pickup, or" +
                    " Shoot: (m-p-s)";
          }
        } else {
          instructions += "You've been slain by the monster! Game over!";
          gameOver();
        }
      } else {
        String howl = this.model.findPit().name();
        String smell = this.model.findMonsters().name();
        if (!howl.equalsIgnoreCase("none")) {
          instructions += "You hear a " + howl + " howl...<br>";
        }
        if (!smell.equalsIgnoreCase("none")) {
          instructions += "There is a " + smell + " smell of something terrible...<br>";
        }
        instructions += "Move, Pickup, or Shoot: (m-p-s)";
      }
    }
    catch (IllegalArgumentException iae) {
      this.view.showErrorMessage(iae.getMessage());
    }
    return instructions;
  }

  private void gameOver() {
    this.view.disableSomeListeners(this);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    System.out.println("action performed");
    if (e.getSource() instanceof JMenuItem) {
      System.out.println("action is an instance of JMenuItem");
      JMenuItem button = (JMenuItem) e.getSource();
      processButtons(button);
    }
    if (e.getSource() instanceof JButton) {
      System.out.println("action is an instance of JButton");
      beginGame();
    }
  }

  private void processButtons(JMenuItem button) {
    String name = button.getActionCommand();
    if (name.compareToIgnoreCase("Restart") == 0) {
      System.out.println("restart has been called");
      this.view.close();
      beginGame();
    }
    else if (name.compareToIgnoreCase("Redesign") == 0) {
      System.out.println("redesign has been called");
      this.view.close();
      setUp();
    }
  }

  private void beginGame() {
    try {
      int[] arguments = processInputs();
      boolean wraps = processWraps();
      System.out.println("commands processed");
      Dungeon model = new DungeonModel(wraps, arguments[0], arguments[1], arguments[2],
              arguments[3], arguments[4]);
      System.out.println(model.printDungeon());
      game(model);
    }
    catch (IllegalArgumentException iae) {
      System.out.println("Is this being called over and over?");
      this.prelaunchView.showErrorMessage(iae.getMessage());
      this.prelaunchView.refresh();
    }
    catch (IllegalStateException ise) {
      this.prelaunchView.showErrorMessage("One of the inputs has not been selected!");
    }
    System.out.println("dungeon created");
  }

  private int[] processInputs() {
    String[] inputs = prelaunchView.arguments();
    System.out.println(Arrays.toString(inputs));
    int[] arguments = new int[inputs.length - 1];
    try {
      for (int i = 1; i < inputs.length; i++) {
        if (inputs[i] == null) {
          throw new IllegalStateException("An input cannot be null!");
        }
        int arg = Integer.parseInt(inputs[i]);
        arguments[i - 1] = arg;
      }
    }
    catch (NumberFormatException e) {
      throw new IllegalArgumentException("Something's gone wrong with numerical inputs. ");
    }
    return arguments;
  }

  private boolean processWraps() {
    String wraps = prelaunchView.arguments()[0];
    System.out.println(wraps);
    if (wraps.toLowerCase().contains("true")) {
      return true;
    }
    else if (wraps.toLowerCase().contains("false")) {
      return false;
    }
    else {
      throw new IllegalArgumentException("Something's gone wrong with wrapping. ");
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    //do nothing
  }

  @Override
  public void keyPressed(KeyEvent e) {
    String instructions = "";
    Character last = this.view.lastKey();
    if (last != null) {
      if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT
              || e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
        this.directionGiven = true;
        System.out.println("location has been pressed!");
        Location.Move direction = this.view.getDirection();
        System.out.println("moving in direction " + direction);
        if (last.equals('m')) {
          instructions = "<html><div style='text-align:center'>" + move(direction)
                  + "</div></html>";
        }
        if (last.equals('s')) {
          instructions = "<html><div style='text-align:center'>Enter a distance (1-9): " +
                  "</div></html>";
        }
        if (!instructions.equals("")) {
          this.view.setInstructions(instructions);
        }
      }
    }
    this.view.refresh();
  }

  @Override
  public void mouseClick(int xClicked, int yClicked) {
    System.out.println("MOUSE CLICKED AT: (" + xClicked + "," + yClicked + ")");
    int[] coords = this.view.getClick();
    int xOne = coords[0];
    int yOne = coords[1];
    int xTwo = coords[2];
    int yTwo = coords[3];
    System.out.println("BOX AT: (" + xOne + "," + yOne + "), (" + xTwo + "," + yTwo + ")");
    Location.Move direction = null;
    if (xClicked > xOne && xClicked < xTwo) { //north or south
      if (yClicked < yOne && yClicked > (yOne - 60)) {
        direction = Location.Move.NORTH;
      }
      else if (yClicked > yTwo && yClicked < (yTwo + 60)) {
        direction = Location.Move.SOUTH;
      }
    }
    else if (yClicked > yOne && yClicked < yTwo) { //west or east
      if (xClicked < xOne && xClicked > (xOne - 60)) {
        direction = Location.Move.WEST;
      }
      else if (xClicked > xTwo && xClicked < (xTwo + 60)) {
        direction = Location.Move.EAST;
      }
    }
    if (direction != null) {
      this.view.setInstructions("<html><div style='text-align:center'>" + move(direction) +
              "</div></html>");
      System.out.println("Issue with dfs or bfs");
    }
    this.view.refresh();
  }
}