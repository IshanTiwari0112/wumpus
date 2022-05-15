import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Board extends JInternalFrame {
    public static final int DEFAULT_WIDTH = 10;
    public static final int DEFAULT_HEIGHT = 10;
    public static final GridBagConstraints BOARD_CONSTRAINTS = new GridBagConstraints();
    public static final GridBagConstraints MOVE_CONSTRAINTS = new GridBagConstraints();

    static {
        BOARD_CONSTRAINTS.gridx = 0;
        BOARD_CONSTRAINTS.gridy = 0;
        BOARD_CONSTRAINTS.gridwidth = 10;
        BOARD_CONSTRAINTS.gridheight = 10;
    }

    private final int boardWidth;
    private final int boardHeight;
    private Tile[][] board;

    /**
     * Constructor
     *
     * @param boardWidth  Integer width of board
     * @param boardHeight Integer height of board
     */
    public Board(int boardWidth, int boardHeight) throws IOException {
        this.boardHeight = boardHeight;
        this.boardWidth = boardWidth;
        drawBoard();
        initialiseBoard();
        ((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);
        this.setLayout(new GridLayout(boardHeight, boardWidth));
    }

    /**
     * default constructor
     */
    @SuppressWarnings("unused")
    public Board() throws IOException {
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    /**
     * Resets the visibility of obstacles each level
     */
    public void resetVisibility() {
        for (Tile[] row : board) {
            for (Tile t : row) {
                if (t.isObstacleVisible()) {
                    t.toggleObstacleVisible();
                }
            }
        }
    }

    /**
     * creates the board array
     */
    private void drawBoard() {
        this.board = new Tile[boardHeight][boardWidth];
    }

    /**
     * initialises all the tiles on the board
     */
    private void initialiseBoard() throws IOException {
        for (int row = 0; row < boardHeight; row++) {
            for (int column = 0; column < boardWidth; column++) {
                Tile tileToBeAdded = new Tile(BoardLocation.of(row, column));
                board[row][column] = tileToBeAdded;
                this.add(tileToBeAdded);
                tileToBeAdded.setVisible(true);

            }
        }
    }

    /**
     * Returns the tile corresponding to a given BoardLocation
     *
     * @param boardLocation Supplied boardLocation
     * @return the tile object at this boardLocation
     */
    public Tile getTile(BoardLocation boardLocation) {
        boardLocation = adjustIndexing(boardLocation);
        return board[boardLocation.getRow()][boardLocation.getColumn()];
    }

    /**
     * Overloaded method to get tile by row and column instead
     *
     * @param row    row
     * @param column column
     * @return tile object at these coordinates on the board
     */
    @SuppressWarnings("unused")
    public Tile getTile(int row, int column) {
        return getTile(BoardLocation.of(row, column));
    }

    /**
     * Adjusts for wraparound on the edges of the board using modular arithmetic
     *
     * @param boardLocation boardlocation to adjust the indexing of
     * @return new boardlocation with updated coordinates to account for toroidal board
     */
    public BoardLocation adjustIndexing(BoardLocation boardLocation) {
        int row = boardLocation.getRow() % boardHeight;
        int column = boardLocation.getColumn() % boardWidth;
        if (row < 0) {
            row += boardHeight;
        }
        if (column < 0) {
            column += boardWidth;
        }
        return BoardLocation.of(row, column);
    }

    /**
     * prints the representation of the board
     */
    public void printBoard() {
        StringBuilder boardText = new StringBuilder();
        for (Tile[] row : board) {
            for (Tile tile : row) {
                if (tile.containsPlayer()) {
                    boardText.append(Player.BOARD_REPRESENTATION);
                } else {
                    boardText.append(Tile.TILE_REPRESENTATION);
                }
            }
            boardText.append("\n");
        }
        System.out.println(boardText);
        System.out.println("\n");
    }

    /**
     * gets the width of the board
     *
     * @return boardWidth
     */
    public int getBoardWidth() {
        return boardWidth;
    }

    /**
     * gets the height of the board
     *
     * @return boardHeight
     */
    public int getBoardHeight() {
        return boardHeight;
    }

    /**
     * Move a player to a new BoardLocation
     *
     * @param player        player to move
     * @param boardLocation location to move to
     * @return anything which happened to the player when they moved to that tile
     */
    public Obstacle.OnEncounter move(Player player, BoardLocation boardLocation) {
        Tile tile = getTile(boardLocation);
        Obstacle.ObstacleType obstacleType;
        if (tile.isEmpty()) {
            obstacleType = Obstacle.ObstacleType.NONE;
        } else {
            obstacleType = tile.getContainedObstacle().getObstacleType();
        }
        switch (obstacleType) {
            case SHIELD:
                getTile(player.getBoardLocation()).removePlayer();
                getTile(boardLocation).movePlayerHere(player);
                getTile(boardLocation).removeObstacle();
                return Obstacle.OnEncounter.SHIELDPOWER;
            case BLIND_EYE:
                getTile(player.getBoardLocation()).removePlayer();
                getTile(boardLocation).movePlayerHere(player);
                getTile(boardLocation).removeObstacle();
                return Obstacle.OnEncounter.BLINDPOWER;
            case HUNTER_EYE:
                getTile(player.getBoardLocation()).removePlayer();
                getTile(boardLocation).movePlayerHere(player);
                getTile(boardLocation).removeObstacle();
                return Obstacle.OnEncounter.HUNTEREYEPOWER;
            case HAWK_EYE:
                getTile(player.getBoardLocation()).removePlayer();
                getTile(boardLocation).movePlayerHere(player);
                getTile(boardLocation).removeObstacle();
                return Obstacle.OnEncounter.HAWKEYEPOWER;
            case SAWMAN:
                getTile(player.getBoardLocation()).removePlayer();
                getTile(boardLocation).movePlayerHere(player);
                getTile(boardLocation).removeObstacle();
                return Obstacle.OnEncounter.SAWMANPOWER;
            case RAILGUN:
                getTile(player.getBoardLocation()).removePlayer();
                getTile(boardLocation).movePlayerHere(player);
                getTile(boardLocation).removeObstacle();
                return Obstacle.OnEncounter.RAILGUNPOWER;
            case WUMPUS:
            case PIT:
                if (player.isShieldPower()) {
                    player.setShield(false);
                    JOptionPane.showMessageDialog(this, "Shield consumed!");
                    return Obstacle.OnEncounter.MOVE;//works perfectly and displays message to user
                }
                return Obstacle.OnEncounter.KILL;
            case BATS:
                return Obstacle.OnEncounter.RANDOM_MOVE;
            case NONE:
                getTile(player.getBoardLocation()).removePlayer();
                getTile(boardLocation).movePlayerHere(player);
                return Obstacle.OnEncounter.MOVE;
            case TREASURE:
                getTile(player.getBoardLocation()).removePlayer();
                getTile(boardLocation).movePlayerHere(player);
                return Obstacle.OnEncounter.TREASURE_COLLECT;
            case EXIT:
                return Obstacle.OnEncounter.EXIT;
            default:
                throw new IllegalStateException("Unexpected value: " + tile.getContainedObstacle().getObstacleType());
        }
    }

    /**
     * Move an obstacle to a boardlocation
     *
     * @param obstacle      obstacle to move
     * @param boardLocation location to move obstacle to
     */
    public void move(Obstacle obstacle, BoardLocation boardLocation) {
        getTile(obstacle.getLocation()).removeObstacle();
        getTile(boardLocation).setContainedObstacle(obstacle);
        obstacle.setLocation(getTile(boardLocation));
    }

    /**
     * Place an obstacle on the board, sort of duplicate code, but makes methods
     * easier to parse by using the word place instead of move when initialising
     * obstacles
     *
     * @param obstacle      obstacle to place at a location
     * @param boardLocation location to place the obstacle at
     */
    public void place(Obstacle obstacle, BoardLocation boardLocation) {
        move(obstacle, boardLocation);
    }

    /**
     * Places the player at a location, used during initialisation
     *
     * @param player        player to place
     * @param boardLocation location to place player at
     */
    public void place(Player player, BoardLocation boardLocation) {
        getTile(boardLocation).movePlayerHere(player);
    }

    /**
     * Prints the messages for a player if they're nearby an obstacle
     *
     * @param player player
     */
    public void printMessages(Player player) {
        System.out.println(getMessages(player));
    }

    /**
     * gets all the messages for a player
     *
     * @param player player to get messages for
     * @return string of messages
     */
    public String getMessages(Player player) {
        StringBuilder finalMessage = new StringBuilder();
        BoardLocation originalLocation = player.getBoardLocation();
        BoardLocation newLocation = BoardLocation.of(originalLocation.row() - 1, originalLocation.column() - 1);
        final int rowFinal = newLocation.row();
        final int colFinal = newLocation.column();
        for (int row = newLocation.row(); row < rowFinal + 3; row++) {
            for (int col = newLocation.column(); col < colFinal + 3; col++) {
                if (!this.getTile(BoardLocation.of(row, col)).isEmpty()) {
                    finalMessage.append(this.getTile(BoardLocation.of(row, col)).getContainedObstacle().getMessage()).append("\n");
                }
            }
        }
        return finalMessage.toString();
    }

    public void clearBoard() {
        for (Tile[] tiles : board) {
            for (Tile t : tiles) {
                t.removeObstacle();
                t.removePlayer();
            }
        }
    }
}
