package dungeon.view;

import java.awt.event.ActionListener;

/**
 * Interface for the prelaunch view of the dungeon game. This interface constructs a view that
 * explains the rules of the game and allows users to give inputs for the game with a JMenu.
 */
public interface PrelaunchView {

  /**
   * Returns the list of arguments to create a dungeon model, as given by the user. This includes
   * the if the dungeon wraps, the dimensions of the dungeon, the degree of interconnectivity, the
   * percent of caves with treasure, and the number of monsters.
   * @return int array of dungeon model arguments
   */
  String[] arguments();

  /**
   * Refresh the view to reflect any changes in the game state.
   */
  void refresh();

  /**
   * Make the view visible to start the game session.
   */
  void makeVisible();

  /**
   * Transmit an error message to the view, in case
   * the command could not be processed correctly.
   * @param error   error that will be shown
   */
  void showErrorMessage(String error);

  /**
   * Provide the view with an action listener for
   * the button that should cause the program to
   * process a command. This is so that when the button
   * is pressed, control goes to the action listener.
   * @param actionEvent   object that's listening for the command button to be clicked
   */
  void setCommandButtonListener(ActionListener actionEvent);

  /**
   * Make the window invisible. Only used once the main view is opened.
   */
  void close();

}
