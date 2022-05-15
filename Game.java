import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Scanner;

import static java.awt.GridBagConstraints.RELATIVE;

public class Game extends JInternalFrame {//wumpus after hunter eye obtained
    //wumpus giving hunter eye for some reason?
    //dead wumpus message
    //treasure collected message
    private final JTextArea level;
    private final JTextArea playerMessages;
    private final JInternalFrame movement;
    private final JInternalFrame shooting;
    private final Board board;
    private final Player player;
    private final ArrayList<Wumpus> wumpusList = new ArrayList<>();
    private final ArrayList<Pit> pitList = new ArrayList<>(); //need access for powers

    private int lvlCount;

    public Game() {
        this.level = new JTextArea();
        this.playerMessages = new JTextArea();
        this.movement = new JInternalFrame();
        this.shooting = new JInternalFrame();
        this.board = null;
        this.player = new Player();
    } //DO NOT USE FOR ANYTHING OTHER THAN TESTING

    /**
     * Constructor
     *
     * @param boardWidth  board width squares
     * @param boardHeight board height squares
     */
    public Game(int boardWidth, int boardHeight) throws IOException {
        //instancing classes and creating GUI objects
        this.board = new Board(boardWidth, boardHeight);
        this.player = new Player();
        this.lvlCount = 1;
        JInternalFrame jFrame = this;
        this.playerMessages = new JTextArea();

        this.movement = new JInternalFrame();
        this.shooting = new JInternalFrame();
        this.level = new JTextArea();
        shooting.setPreferredSize(new Dimension(150, 150));
        movement.setPreferredSize(new Dimension(150, 150));
        //hides the title bar of the internal frame
        ((javax.swing.plaf.basic.BasicInternalFrameUI) shooting.getUI()).setNorthPane(null);
        ((javax.swing.plaf.basic.BasicInternalFrameUI) movement.getUI()).setNorthPane(null);

        setupButtons(movement, ActionType.MOVE);
        if (!player.isBlindPower()) {
            setupButtons(shooting, ActionType.SHOOT);
        }
        //whoever designed object-oriented GUIs deserves a special place in hell I think
        //maybe I'm just bad at them. The following code is a horror
        GridBagConstraints playerMessagesConstraints = new GridBagConstraints();
        GridBagConstraints levelConstraints = new GridBagConstraints();
        GridBagConstraints movementConstraints = new GridBagConstraints();
        GridBagConstraints shootingConstraints = new GridBagConstraints();
        GridBagConstraints movementLabelConstraints = new GridBagConstraints();
        GridBagConstraints shootingLabelConstraints = new GridBagConstraints();

        JLabel shootingLabel = new JLabel("Shoot");
        JLabel movingLabel = new JLabel("Move");

        movementLabelConstraints.gridx = 11;
        movementLabelConstraints.gridy = 2;
        movementLabelConstraints.gridheight = 1;

        shootingLabelConstraints.gridx = 11;
        shootingLabelConstraints.gridy = 6;
        shootingLabelConstraints.gridheight = 1;

        levelConstraints.gridx = 11;
        levelConstraints.gridy = 0;
        levelConstraints.gridheight = 1;

        playerMessagesConstraints.gridx = 11;
        playerMessagesConstraints.gridy = 1;
        playerMessagesConstraints.gridheight = 1;

        movementConstraints.gridx = 11;
        movementConstraints.gridy = 3;
        movementConstraints.gridwidth = 3;
        movementConstraints.gridheight = 3;

        shootingConstraints.gridx = 11;
        shootingConstraints.gridy = RELATIVE;
        shootingConstraints.gridwidth = 3;
        shootingConstraints.gridheight = 3;


        jFrame.setLayout(new GridBagLayout());
        jFrame.setVisible(true);
        board.setVisible(true);
        level.setText("Level: " + lvlCount);
        level.setVisible(true);
        level.setEditable(false);
        playerMessages.setVisible(true);
        playerMessages.setEditable(false);
        jFrame.add(board, Board.BOARD_CONSTRAINTS);
        jFrame.add(playerMessages, playerMessagesConstraints);
        jFrame.add(movement, movementConstraints);
        jFrame.add(movingLabel, movementLabelConstraints);
        jFrame.add(level, levelConstraints);
        jFrame.add(shootingLabel, shootingLabelConstraints);
        jFrame.add(shooting, shootingConstraints);
        jFrame.setSize(new Dimension(500, 500));
    }

    /**
     * Setup buttons in 3x3 grid leaving spaces so they're correctly setup
     * @param buttonFrame frame to put them in
     * @param actionType type of action the button should do
     */
    public void setupButtons(JInternalFrame buttonFrame, ActionType actionType) {
        buttonFrame.setSize(new Dimension(120, 120));
        Dimension buttonDimension = new Dimension(40, 40);
        buttonFrame.setLayout(new GridLayout(3, 3, 4, 4));
        BasicArrowButton north = new BasicArrowButton(SwingConstants.NORTH);
        BasicArrowButton east = new BasicArrowButton(SwingConstants.EAST);
        BasicArrowButton south = new BasicArrowButton(SwingConstants.SOUTH);
        BasicArrowButton west = new BasicArrowButton(SwingConstants.WEST);


        BasicArrowButton[] buttonArray = new BasicArrowButton[4];
        buttonArray[0] = north;
        buttonArray[1] = east;
        buttonArray[2] = south;
        buttonArray[3] = west;

        ActionListener northListener = e -> singleInstancePlay(new Action(actionType, ActionDirection.UP));
        ActionListener eastListener = e -> singleInstancePlay(new Action(actionType, ActionDirection.RIGHT));
        ActionListener westListener = e -> singleInstancePlay(new Action(actionType, ActionDirection.LEFT));
        ActionListener southListener = e -> singleInstancePlay(new Action(actionType, ActionDirection.DOWN));

        north.addActionListener(northListener);
        east.addActionListener(eastListener);
        west.addActionListener(westListener);
        south.addActionListener(southListener);

        for (BasicArrowButton b : buttonArray) {
            b.setPreferredSize(buttonDimension);
        }

        buttonFrame.add(new JLabel(""));
        buttonFrame.add(north);
        buttonFrame.add(new JLabel(""));
        buttonFrame.add(west);
        buttonFrame.add(new JLabel(""));
        buttonFrame.add(east);
        buttonFrame.add(new JLabel(""));
        buttonFrame.add(south);
        buttonFrame.add(new JLabel(""));
        buttonFrame.setVisible(true);
    }

    /**
     * Resets the board state for a new level
     */
    public void reset() {
        board.resetVisibility();
        board.clearBoard();
        movement.setVisible(true);
        shooting.setVisible(true);
        playerMessages.setVisible(true);
        player.setAlive(true);
        player.setTreasureCollect(false);
        player.setBlind(false);
        player.setHawk(false);
        player.setHunter(false);
        player.setRailgun(false);
        player.setShield(false);
        player.setSawman(false);
        lvlCount++;
        populateBoard(this.lvlCount);
    }

    /**
     * Tells the player they've reached the next level
     */
    public void progressToNextLevel() {
        messageBox("Congratulations!", "You won! Proceed to the next level?");
        level.setText("Level: " + lvlCount);
    }

    /**
     * Main play loop
     * @param actionToBeTaken action being taken in this instance of play
     */
    public void singleInstancePlay(Action actionToBeTaken) {
        if (actionToBeTaken.actionType == ActionType.MOVE) {
            //move
            Obstacle.OnEncounter encounter = board.move(player, BoardLocation.fromAction(player.getBoardLocation(), actionToBeTaken));
            switch (encounter) {
                case KILL:
                    messageBox("You died!", "You died! Play again?");
                    return;
                case RANDOM_MOVE:
                    board.move(player, Randomise.randomLocation(board, true));
                    break;
                case MOVE:
                    break;
                case HUNTEREYEPOWER:
                    for (Wumpus wumpus : wumpusList) {

                        Tile wumpusTile = board.getTile(wumpus.getLocation());
                        wumpusTile.toggleObstacleVisible();
                    }
                    player.setHunter(true);
                    informationBox("Hunter eye obtained shows possible locations for a wumpus at time of pickup", "Power");
                    break;
                case NOTHING:
                    break;
                case TREASURE_COLLECT:
                    player.setTreasureCollect(true);
                    informationBox("Treasure collected, can proceed to the exit", "Treasure");
                    break;
                case EXIT:
                    if (player.isTreasureCollected()) {
                        progressToNextLevel();
                    }
                    break;
                case SAWMANPOWER:
                    player.setSawman(true);
                    shooting.setVisible(false);
                    informationBox("Sawman ability activated : can no longer shoot", "Handicap");
                    break;
                case HAWKEYEPOWER:
                    for (Pit pit : pitList) {
                        Tile pitTile = board.getTile(pit.getLocation());
                        pitTile.toggleObstacleVisible();
                    }
                    player.setHawk(true);
                    informationBox("Hawk eye obtained:shows possible locations for a pit at time of pickup", "Power");
                    break;
                case RAILGUNPOWER:
                    player.setRailgun(true);
                    informationBox("Railgun obtained : can shoot upto 3 tiles away in one shot", "Power");
                    break;
                case BLINDPOWER:
                    player.setBlind(false);
                    playerMessages.setVisible(false);
                    informationBox("Blind ability activated :no messages or warnings will now be shown", "Handicap");
                    break;
                case SHIELDPOWER:
                    player.setShield(true);
                    informationBox("Shield obtained : can cheat death once", "Power");
                    break;
                default:
                    break;
            }
        } else if (actionToBeTaken.actionType == ActionType.SHOOT) {
            boolean wumpusNotKilled = true;
            //shoot
            BoardLocation shotLocation = BoardLocation.fromAction(player.getBoardLocation(), actionToBeTaken);
            Tile shotTile = board.getTile(shotLocation);
            if (player.isRailgunPower() && wumpusNotKilled) {
                for (int i = 0; i < 3; i++) {
                    shotLocation = BoardLocation.fromAction(shotLocation, actionToBeTaken);
                    shotTile = board.getTile(shotLocation);
                    if (!(shotTile.isEmpty()) && shotTile.getContainedObstacle().getObstacleType() == Obstacle.ObstacleType.WUMPUS) {
                        //kill wumpus
                        shotTile.removeObstacle();
                        informationBox("wumpus killed", "wumpus dead");
                        wumpusNotKilled = false;
                    }
                }
            } else {
                shootFunction(shotTile);
                wumpusNotKilled = false;
            }

            if (wumpusNotKilled) {
                wumpusMiss();
            }
        }
        playerMessages.setText(board.getMessages(player));
    }

    /**
     * Shoots the chosen tile
     * @param shotTile tile to shoot
     */
    public void shootFunction(Tile shotTile) {
        if (!(shotTile.isEmpty()) && shotTile.getContainedObstacle().getObstacleType() == Obstacle.ObstacleType.WUMPUS) {
            //kill wumpus
            shotTile.removeObstacle();
            informationBox("wumpus killed", "wumpus dead");
        }
    }

    /**
     * For when the player shoots but misses the wumpus
     */
    public void wumpusMiss() {
        for (Wumpus wumpus : wumpusList) {
            board.move(wumpus, Randomise.newWumpusLocation(wumpus.getLocation()));
            if (wumpus.getLocation() == player.getBoardLocation()) {
                if (messageBox("You died! Play again?", "You died!")) return;
            }
        }
    }

    /**
     * Shows info to the player
     * @param message message string
     * @param title messagebox title
     */
    public void informationBox(String message, String title) {
        JOptionPane.showMessageDialog(board, message, title, JOptionPane.OK_OPTION);
    }

    /**
     * Gives the user the option to exit the game
     * @param messageText message text
     * @param messageTitle message title
     * @return true if they choose to continue.
     */
    public boolean messageBox(String messageText, String messageTitle) {
        int result = JOptionPane.showConfirmDialog(board, messageTitle, messageText,
                JOptionPane.YES_NO_OPTION);
        player.setAlive(false);
        board.getTile(player.getBoardLocation()).setBackground(Color.WHITE);
        movement.setVisible(false);
        shooting.setVisible(false);
        if (result == 0) {
            reset();
            return true;
        } else {
            System.exit(0);
        }
        return false;
    }

    /**
     * Places everything on the board
     *
     * @param lvl level which the player is on
     */
    public void populateBoard(int lvl) {
        int wumpusCount = (lvl / 5) + 1; //number of wumpus increases by 1 every 5 levels
        int batCount = (lvl / 5) + 1;
        int pitCount = (lvl / 10) + 1;

        ArrayList<Bats> batList = new ArrayList<>();
        //ArrayList<Pit> pitList = new ArrayList<>();

        try {
            //place things on board and make sure they're not on top of each other
            for (int i = 0; i < wumpusCount; i++) {
                BoardLocation wumpusLocation = Randomise.randomLocation(board, true);
                wumpusList.add(i, new Wumpus(board.getTile(wumpusLocation)));
                board.place(wumpusList.get(i), wumpusLocation);
            }
            for (int i = 0; i < pitCount; i++) {
                BoardLocation pitLocation = Randomise.randomLocation(board, true);
                pitList.add(i, new Pit(board.getTile(pitLocation)));
                board.place(pitList.get(i), pitLocation);
            }
            BoardLocation playerLocation = Randomise.randomLocation(board, false);
            board.place(player, playerLocation);
            for (int i = 0; i < batCount; i++) {
                BoardLocation batLocation = Randomise.randomLocation(board, false);
                batList.add(i, new Bats(board.getTile(batLocation)));
                board.place(batList.get(i), batLocation);
            }
            BoardLocation treasureLocation = Randomise.randomLocation(board, false);
            board.place(new Treasure(board.getTile(treasureLocation)), treasureLocation);

            BoardLocation exitLocation = Randomise.randomLocation(board, false);
            board.place(new Exit(board.getTile(exitLocation)), exitLocation);

            BoardLocation hunterLocation = Randomise.randomLocation(board, false);
            board.place(new Hunter(board.getTile(hunterLocation)), hunterLocation);

            BoardLocation hawkLocation = Randomise.randomLocation(board, false);
            board.place(new Hawk(board.getTile(hawkLocation)), hawkLocation);

            BoardLocation sawmanLocation = Randomise.randomLocation(board, false);
            board.place(new Sawman(board.getTile(sawmanLocation)), sawmanLocation);

            BoardLocation railgunLocation = Randomise.randomLocation(board, false);
            board.place(new Railgun(board.getTile(railgunLocation)), railgunLocation);

            BoardLocation blindLocation = Randomise.randomLocation(board, false);
            board.place(new Blind(board.getTile(blindLocation)), blindLocation);

            BoardLocation shieldLocation = Randomise.randomLocation(board, false);
            board.place(new Shield(board.getTile(shieldLocation)), shieldLocation);

        } catch (TileOccupiedException e) {
            e.printStackTrace();
        }
        playerMessages.setText(board.getMessages(player));
    }

    /**
     * Prompts the player to give a command, catching errors in the input
     *
     * @return HashMap of action and direction
     */
    public HashMap<ActionType, ActionDirection> prompt() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input move:");
        String[] move = scanner.nextLine().toLowerCase(Locale.ROOT).trim().split("\\s");
        String identifier;
        String direction;
        try {
            identifier = move[0];
            direction = move[1];
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Invalid move!");
            return (prompt());
        }
        HashMap<ActionType, ActionDirection> moveDetails = new HashMap<>();
        try {
            moveDetails.put(getTypeAsEnum(identifier), getDirectionAsEnum(direction));
            return moveDetails;
        } catch (InvalidMoveException e) {
            System.out.println("Invalid move!");
            return (prompt());
        }
    }

    /**
     * Converts textual input to action direction type
     *
     * @param input text input from user
     * @return ActionDirection specifying the direction as an enum
     * @throws InvalidParameterException if the input doesn't match one of the defined directions
     */
    public ActionDirection getDirectionAsEnum(String input) throws InvalidMoveException {
        switch (input) {
            case "up":
            case "north":
                return ActionDirection.UP;
            case "down":
            case "south":
                return ActionDirection.DOWN;
            case "left":
            case "west":
                return ActionDirection.LEFT;
            case "right":
            case "east":
                return ActionDirection.RIGHT;
            default:
                throw new InvalidMoveException("That direction (" + input + ") is not allowed, try up, down, right, " +
                        "left, north, south, east, west");
        }
    }
//keep exit location and treasure location as attribute of player and set to true when visited and check for treasure on exit enabling player to leave

    //old play method, unused.
    /*
    public void play() {
        boolean gamePlaying = true;
        populateBoard();
        while(gamePlaying) {
            //board.printMessages(player);
            //board.printBoard();
            Action actionToBeTaken = new Action(prompt());
            if(actionToBeTaken.actionType == ActionType.MOVE) {
                //move
                Obstacle.OnEncounter encounter = board.move(player, BoardLocation.fromAction(player.getBoardLocation(), actionToBeTaken));
                if(encounter == Obstacle.OnEncounter.KILL){
                    System.out.println(DEATH_MESSAGE);
                    gamePlaying = false;
                }
                else if(encounter == Obstacle.OnEncounter.RANDOM_MOVE) {
                    board.move(player, Randomise.randomLocation(board, true));
                }
            }
            else if(actionToBeTaken.actionType == ActionType.SHOOT) {
                //shoot
                Tile shotTile = board.getTile(BoardLocation.fromAction(player.getBoardLocation(), actionToBeTaken));
                if(!(shotTile.isEmpty()) && shotTile.getContainedObstacle().getObstacleType() == Obstacle.ObstacleType.WUMPUS) {
                    shotTile.removeObstacle();
                    System.out.println("You killed a wumpus!");
                }
                else {
                    board.move(wumpus, Randomise.newWumpusLocation(wumpus.getLocation()));
                }
                //get location of shot  -done
                //check location for wumpus -done
                // if same then kill wumpus - done
                // else move wumpus - check how to get location ...movement done
            }

        }


    }

     */

    /**
     * Gets action type as enum
     *
     * @param input action type as string
     * @return action type as enum
     * @throws InvalidParameterException action type is not permitted
     */
    public ActionType getTypeAsEnum(String input) throws InvalidMoveException {
        switch (input) {
            case "move":
                return ActionType.MOVE;
            case "shoot":
                return ActionType.SHOOT;
            default:
                throw new InvalidMoveException("That is not a permitted action (" + input + "). " +
                        "Try 'move' or 'shoot'");
        }
    }

    /**
     * Direction of the action
     */
    public enum ActionDirection {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    /**
     * Type of action
     */
    public enum ActionType {
        SHOOT,
        MOVE
    }

    /**
     * Nested class to standardise actions from text input
     */
    public static final class Action {
        public final ActionType actionType;
        public final ActionDirection actionDirection;

        public Action(ActionType t, ActionDirection d) {
            this.actionType = t;
            this.actionDirection = d;
        }

        public Action(HashMap<ActionType, ActionDirection> input) {
            this.actionType = (ActionType) input.keySet().toArray()[0];
            this.actionDirection = input.get(actionType);
        }
    }
}
