package dungeon.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.FlowLayout;
import java.awt.Dimension;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.SwingConstants;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;


/**
 * This is the class for the prelaunch view, which implements PrelaunchView and ActionListener.
 * This class contains methods that construct a view for setting up the dungeon game, receiving
 * inptus and passing these back to the controller.
 */
public class PrelaunchViewImpl extends JFrame implements PrelaunchView, ActionListener {
  private JButton commandButton;
  private String[] inputs;

  /**
   * A constructor for the prelaunch view.
   */
  public PrelaunchViewImpl() {
    super("Dungeon Game Setup");

    this.setSize(750, 750);
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    this.setLayout(new BorderLayout());


    //initialize inputs
    this.inputs = new String[6];

    JPanel middle = new JPanel();
    middle.setLayout(new BorderLayout());
    this.add(middle);

    //header
    JLabel header = new JLabel("<html><div style='text-align:center'>Welcome to the dungeon " +
            "game!<br>To begin the game, please provide some parameters for your dungeon:</div>" +
            "</html>", SwingConstants.CENTER);
    header.setFont(new Font("Serif", Font.BOLD, 20));
    this.add(header, BorderLayout.NORTH);

    JLabel intro = new JLabel("<html><br>(1) In this game, dungeons can wrap. If you think of" +
            " a dungeon as a 2D grid this means that<br>going South from the bottom of the grid " +
            "in a wrapping dungeon will enable the player to enter<br>a location at the top of " +
            "the dungeon. Please enter 'true' for a wrapping dungeon or 'false' for a <br>" +
            "non-wrapping dungeon:<br><br>(2) Please enter your dungeon's desired width:<br><br>" +
            "(3) Please enter your dungeon's desired height:<br><br>(4) Dungeons " +
            "are constructed with a degree of interconnectivity. Interconnectivity = 0<br>when " +
            "there is exactly one path from every cave in the dungeon to every other cave.<br>" +
            "Increasing the interconnectivity increases the number of paths between caves.<br>" +
            "Please enter your dungeon's desired degree of interconnectivity:<br><br>(5) Treasure" +
            " is found in some of the caves in this dungeon.<br>Please enter the percent of caves" +
            " in the dungeon that you would like to fill with treasure:<br><br>(6) There are" +
            " monsters in this game! A player wins if they successfully reach the end location" +
            "<br>without being killed! A player has 3 arrows at the game's start. An arrow will " +
            "hit a monster only<br>if the distance the arrow has been shot is the EXACT distance " +
            "the monster is from the player.<br>Please enter the number of monsters in the " +
            "dungeon: <br></html>");
    middle.add(intro, BorderLayout.NORTH);

    //Create and populate a menu.
    addMenu(middle);

    //button panel
    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new FlowLayout());
    this.add(buttonPanel, BorderLayout.SOUTH);

    //other buttons
    commandButton = new JButton("Begin");
    buttonPanel.add(commandButton);

    JButton quitButton = new JButton("Quit");
    quitButton.addActionListener((ActionEvent e) -> System.exit(0));
    buttonPanel.add(quitButton);

    this.setMinimumSize(new Dimension(500, 500));
    this.pack();
    this.setLocationRelativeTo(null);
  }

  private void addMenu(JPanel middle) {
    JMenuBar menuBar = new JMenuBar();
    menuBar.setLayout(new FlowLayout());
    middle.add(menuBar, BorderLayout.SOUTH);

    JMenu wraps = new JMenu("Wraps");
    menuBar.add(wraps);
    JMenuItem t = new JMenuItem("true");
    t.addActionListener(this);
    wraps.add(t);
    t.setName("Wraps");
    JMenuItem f = new JMenuItem("false");
    f.addActionListener(this);
    wraps.add(f);
    f.setName("Wraps");

    JMenu width = new JMenu("Width");
    menuBar.add(width);
    JMenuItem[] widths = new JMenuItem[10];
    JMenu height = new JMenu("Height");
    menuBar.add(height);
    JMenuItem[] heights = new JMenuItem[10];
    JMenu intercon = new JMenu("Interconnectivity");
    menuBar.add(intercon);
    JMenuItem[] intercons = new JMenuItem[10];
    JMenu treasure = new JMenu("Treasure %");
    menuBar.add(treasure);
    JMenuItem[] treasures = new JMenuItem[10];
    JMenu monster = new JMenu("Monsters");
    menuBar.add(monster);
    JMenuItem[] monsters = new JMenuItem[10];
    for (int i = 0; i < 10; i++) {
      String dimensions = Integer.toString((i + 1) * 2);
      widths[i] = new JMenuItem(dimensions);
      widths[i].addActionListener(this);
      width.add(widths[i]);
      widths[i].setName("Width");
      heights[i] = new JMenuItem(dimensions);
      heights[i].addActionListener(this);
      height.add(heights[i]);
      heights[i].setName("Height");

      intercons[i] = new JMenuItem(Integer.toString(i));
      intercons[i].addActionListener(this);
      intercon.add(intercons[i]);
      intercons[i].setName("Interconnectivity");
      treasures[i] = new JMenuItem(Integer.toString((i + 1) * 10));
      treasures[i].addActionListener(this);
      treasure.add(treasures[i]);
      treasures[i].setName("Treasure");
      monsters[i] = new JMenuItem(Integer.toString(i + 1));
      monsters[i].addActionListener(this);
      monster.add(monsters[i]);
      monsters[i].setName("Monster");
    }
  }

  @Override
  public void close() {
    setVisible(false);
  }

  @Override
  public String[] arguments() {
    String[] arguments = this.inputs;
    return arguments;
  }

  @Override
  public void refresh() {
    repaint();
    System.out.println("refreshed!");
  }

  @Override
  public void makeVisible() {
    setVisible(true);
  }

  @Override
  public void showErrorMessage(String error) {
    JOptionPane.showMessageDialog(this,error,"Error",JOptionPane.ERROR_MESSAGE);
  }

  @Override
  public void setCommandButtonListener(ActionListener actionEvent) {
    commandButton.addActionListener(actionEvent);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (!(e.getSource() instanceof JMenuItem)) {
      throw new IllegalStateException("Uh oh, I'm listening to events I shouldn't be");
    }
    JMenuItem myItem = (JMenuItem) e.getSource();
    String menu = myItem.getName();
    System.out.println(menu);
    switch (menu) {
      case "Wraps":
        this.inputs[0] = myItem.getActionCommand();
        System.out.println(myItem.getActionCommand());
        break;
      case "Width":
        this.inputs[1] = myItem.getActionCommand();
        break;
      case "Height":
        this.inputs[2] = myItem.getActionCommand();
        break;
      case "Interconnectivity":
        this.inputs[3] = myItem.getActionCommand();
        break;
      case "Treasure":
        this.inputs[4] = myItem.getActionCommand();
        break;
      case "Monster":
        this.inputs[5] = myItem.getActionCommand();
        break;
      default:
        throw new IllegalStateException("Something went wrong. ");
    }
  }
}