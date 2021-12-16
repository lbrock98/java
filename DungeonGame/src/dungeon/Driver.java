package dungeon;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import dungeon.control.GraphicController;
import dungeon.control.GraphicControllerImpl;
import dungeon.control.TextControllerImpl;
import dungeon.model.Dungeon;
import dungeon.model.DungeonModel;
import dungeon.view.PrelaunchView;
import dungeon.view.PrelaunchViewImpl;

/**
 * This class is the driver class that will pass control to either the text controller or the
 * graphical controller, depending on the command-line argument.
 */
public class Driver {

  /**
   * Run a game interactively in the console or with a GUI.
   */
  public static void main(String[] args) {
    try {
      if (args[0].equals("text")) {
        Readable input = new InputStreamReader(System.in);
        Appendable output = System.out;
        Scanner scan = new Scanner(input);
        Dungeon dungeon = gameSetup(scan, output);
        new TextControllerImpl(input, output).game(dungeon);
      }
      else if (args[0].equals("graphics")) {
        PrelaunchView prelaunchView = new PrelaunchViewImpl();
        GraphicController controller = new GraphicControllerImpl(prelaunchView);
        controller.setUp();
      }
      else {
        throw new ArrayIndexOutOfBoundsException("Incorrect argument");
      }
    }
    catch (ArrayIndexOutOfBoundsException aiobe) {
      System.out.println("Please run this file with an argument 'text' for text-based mode, " +
              "or 'graphics' for graphical mode. ");
    }
  }


  private static Dungeon gameSetup(Scanner scan, Appendable out) {
    try {
      out.append("Welcome to the dungeon game! To begin the game, please provide some parameters" +
              " for the dungeon: \n");
    }
    catch (IOException ioe) {
      throw new IllegalStateException("Welcome message append failed", ioe);
    }
    int width = 0;
    int height = 0;
    Boolean wraps = false;
    int con = 0;
    int treasure = 0;
    int monster = 0;
    while (true) {
      try {
        out.append("(1) Please enter your dungeon's desired width: \n");
        String widthString = scan.next();
        width = Integer.parseInt(widthString);

        out.append("(2) Please enter your dungeon's desired height: \n");
        String heightString = scan.next();
        height = Integer.parseInt(heightString);

        out.append("(3) In this game, dungeons can wrap. If you think of a dungeon as a 2D grid, " +
                "this means that going South from the bottom \nof the grid in a wrapping dungeon " +
                "will enable the player to enter a location at the top of the dungeon.\nPlease " +
                "enter 'true' for a wrapping dungeon or 'false' for a non-wrapping dungeon: \n");
        while (true) {
          String wrapString = scan.next();
          if (!wrapString.toLowerCase().contains("true") &&
                  !wrapString.toLowerCase().contains("false")) {
            try {
              out.append("This input is invalid. Please enter 'true' for a wrapping dungeon or " +
                      "'false' for a non-wrapping dungeon:\n");
              continue;
            } catch (IOException ioe) {
              throw new IllegalStateException("Boolean message fails.", ioe);
            }
          }
          wraps = Boolean.parseBoolean(wrapString);
          break;
        }

        out.append("(4) Dungeons are also constructed with a degree of interconnectivity. We " +
                "define an interconnectivity = 0\nwhen there is exactly one path from every cave" +
                " in the dungeon to every other cave in the dungeon. Increasing the degree of" +
                " \ninterconnectivity increases the number of paths between caves. \nPlease enter" +
                " your dungeon's desired degree of interconnectivity: \n");
        String conString = scan.next();
        con = Integer.parseInt(conString);

        out.append("(5) Treasure is found in some of the caves in this dungeon. \nPlease enter " +
                "the percent of caves in the dungeon that you would like to fill with treasure." +
                " \n");
        String treasureString = scan.next();
        treasure = Integer.parseInt(treasureString);

        out.append("(6) There are also monsters in this game! A player wins a game if they " +
                "successfully reach the end location without being killed! \nA player is " +
                "equipped with 3 arrows at the game's start, and has the option to shoot " +
                "arrows in a direction and at a specified distance. \nAn arrow will hit a " +
                "monster only if the distance the arrow has been shot is the EXACT distance" +
                " between the player and the monster.\nPlease enter the number of monsters in " +
                "the dungeon: \n");
        String monsterString = scan.next();
        monster = Integer.parseInt(monsterString);
      } catch (IOException ioe) {
        throw new IllegalStateException("Game setup appends failed", ioe);
      }
      catch (NumberFormatException nfe) {
        try {
          out.append("One of your inputs is invalid. Please ensure that your width, " +
                  "height, interconnectivity, percent of treasure,\nand number of monsters are " +
                  "all integers written with numerals. For example, you would write '1' instead " +
                  "of 'one'.\n");
        } catch (IOException ioe) {
          throw new IllegalStateException("NumberFormat message fails.", ioe);
        }
        continue;
      }
      try {
        Dungeon dungeon = new DungeonModel(wraps, width, height, con, treasure, monster);
        return dungeon;
      } catch (IllegalArgumentException iae) {
        try {
          out.append("Something is wrong with one of your inputs. Please ensure that your width " +
                  "and height are between 3 and 100.\nEnsure that interconnectivity is greater " +
                  "than or equal to 0 and less than the size of your dungeon. Ensure that percent" +
                  "\nof treasure is between 0 and 100. Ensure that there are not more monsters " +
                  "than dungeon caves.\n");
        } catch (IOException ioe) {
          throw new IllegalStateException("IllegalArgument message fails.", ioe);
        }
      }
    }
  }


}
