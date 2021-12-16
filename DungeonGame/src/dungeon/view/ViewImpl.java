package dungeon.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.FlowLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import javax.swing.JMenuBar;

import dungeon.control.GraphicController;
import dungeon.model.Location;
import dungeon.model.ReadOnlyDungeon;

/**
 * This class implements the View interface and KeyListener. It provides methods that construct a
 * user interface that takes in actions from the user, and either reacts or passes these actions
 * to the controller.
 */
public class ViewImpl extends JFrame implements View, KeyListener {
  private JMenuItem redesign;
  private JMenuItem restart;
  private GameGrid gameGrid;
  private JLabel instructions;
  private Character lastKey;
  private ReadOnlyDungeon model;
  private JLabel player;
  private JLabel top;
  private Location.Move direction;

  /**
   * A constructor for the view.
   * @param model   a read-only model of the dungeon
   */
  public ViewImpl(ReadOnlyDungeon model) {
    super("Dungeon Game");
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null. ");
    }
    this.model = model;
    System.out.println("CALLING VIEWIMPL");

    this.lastKey = null;
    this.direction = null;
    this.setSize(1000, 625);
    this.setLayout(new BorderLayout());
    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    //add top label, which includes location information and instructions
    JPanel header = new JPanel(new GridLayout(0, 1));
    this.add(header, BorderLayout.NORTH);
    Location currentLoc = model.getPlayer().getLocation();
    String moves = model.description(currentLoc);
    String[] lines = moves.split("\\.");
    moves = "<html><div style='text-align:center'>Your objective is to reach the end cave without" +
            " being killed! Collect as much treasure along the way as you can!<br>" + lines[0]
            + "<br>" + lines[1] + "</div></html>";
    this.top = new JLabel(moves, SwingConstants.CENTER);
    top.setFont(new Font("Serif", Font.BOLD, 20));
    header.add(top);
    this.instructions = new JLabel("Move, Pickup, or Shoot: (m-p-s)", SwingConstants.CENTER);
    this.instructions.setFont(new Font("Serif", Font.BOLD, 20));
    header.add(this.instructions);

    //add bottom panel, which includes hint and buttons, and player's description
    JPanel bottom = new JPanel(new GridLayout(0,1));
    this.add(bottom, BorderLayout.SOUTH);
    //JPanel buttonPanel = new JPanel(new FlowLayout());
    //bottom.add(buttonPanel, BorderLayout.SOUTH);
    String playerDesc = "<html><div style='text-align:center'>" +
            model.description(model.getPlayer()) + "</div><html>";
    this.player = new JLabel(playerDesc, SwingConstants.CENTER);
    this.player.setFont(new Font("Serif", Font.BOLD, 20));
    bottom.add(player, BorderLayout.NORTH);
    JLabel hints = new JLabel("<html><div style='text-align:center'>Hint: Beware the wolf " +
            "pit!</div><html>", SwingConstants.CENTER);
    hints.setFont(new Font("Serif", Font.ITALIC, 18));
    bottom.add(hints);
    addMenu(bottom);

    //add game grid, make scrollable
    JPanel container = new JPanel(new BorderLayout());
    this.gameGrid = new GameGrid(model);
    container.add(gameGrid);

    int width = model.getDimensions()[0];
    int height = model.getDimensions()[1];
    container.setPreferredSize(new Dimension(width * 60 + 20, height * 60 + 20));
    JScrollPane scrollPane = new JScrollPane(container);
    this.add(scrollPane);

    this.setLocationRelativeTo(null);
  }

  private void updateText() {
    this.player.setText("<html><div style='text-align:center'>" +
            model.description(model.getPlayer()) + "</div><html>");

    Location currentLoc = model.getPlayer().getLocation();
    String moves = model.description(currentLoc);
    String[] lines = moves.split("\\.");
    moves = "<html><div style='text-align:center'>Your objective is to reach the end cave " +
            "without being killed! Collect as much treasure along the way as you can!<br>" +
            lines[0] + "<br>" + lines[1] + "</div></html>";
    this.top.setText(moves);
  }

  private void addMenu(JPanel p) {
    JMenuBar menuBar = new JMenuBar();
    menuBar.setLayout(new FlowLayout());
    p.add(menuBar);

    JMenuItem quit = new JMenuItem("Quit");
    menuBar.add(quit);
    quit.addActionListener((ActionEvent e) -> System.exit(0));

    this.restart = new JMenuItem("Restart");
    menuBar.add(restart);

    this.redesign = new JMenuItem("Redesign");
    menuBar.add(redesign);
  }

  @Override
  public void setInstructions(String text) {
    this.instructions.setText(text);
  }

  @Override
  public void disableSomeListeners(KeyListener keys) {
    this.removeKeyListener(this);
    this.removeKeyListener(keys);
  }

  @Override
  public void showErrorMessage(String error) {
    JOptionPane.showMessageDialog(this,error,"Error",JOptionPane.ERROR_MESSAGE);
  }

  @Override
  public void addListeners(ActionListener clicks, KeyListener keys, GraphicController cont) {
    this.restart.addActionListener(clicks);
    this.redesign.addActionListener(clicks);
    this.addKeyListener(this);
    this.addKeyListener(keys);
    MouseAdapter clickAdapter = new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        int x = e.getX();
        int y = e.getY();
        cont.mouseClick(x,y);
        refresh();
      }
    };
    this.gameGrid.addMouseListener(clickAdapter);
  }

  @Override
  public void refresh() {
    System.out.println("refresh");
    System.out.println(this.model.description(this.model.getPlayer()));
    repaint();
    updateText();
  }

  @Override
  public void makeVisible() {
    setVisible(true);
  }

  @Override
  public void close() {
    setVisible(false);
  }

  @Override
  public void keyTyped(KeyEvent e) {
    switch (e.getKeyChar()) {
      case 'm':
        System.out.println("move!");
        this.instructions.setText("Choose a direction to move using arrow keys or clicking on a " +
                "valid space.");
        this.lastKey = 'm';
        break;
      case 's':
        System.out.println("shoot!");
        this.instructions.setText("Choose a direction to shoot using arrow keys.");
        this.lastKey = 's';
        break;
      default:
        //do nothing
    }
  }

  @Override
  public Location.Move getDirection() {
    Location.Move move = this.direction;
    return move;
  }

  @Override
  public Character lastKey() {
    Character lastKey = this.lastKey;
    return lastKey;
  }

  @Override
  public void keyPressed(KeyEvent e) {
    switch (e.getKeyCode()) {
      case KeyEvent.VK_LEFT:
        this.direction = Location.Move.WEST;
        System.out.println("pressed left");
        break;
      case KeyEvent.VK_RIGHT:
        this.direction = Location.Move.EAST;
        System.out.println("pressed right");
        break;
      case KeyEvent.VK_UP:
        this.direction = Location.Move.NORTH;
        break;
      case KeyEvent.VK_DOWN:
        this.direction = Location.Move.SOUTH;
        break;
      default:
        //do nothing
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    //do nothing
  }

  @Override
  public int[] getClick() {
    int[] coords = new int[4];
    coords[0] = this.gameGrid.getPlayerLoc()[0];
    coords[1] = this.gameGrid.getPlayerLoc()[1];
    coords[2] = this.gameGrid.getPlayerLoc()[2];
    coords[3] = this.gameGrid.getPlayerLoc()[3];
    return coords;
  }

}