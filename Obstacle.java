import javax.swing.*;

public abstract class Obstacle {
    private final ObstacleType obstacleType;
    private final String message;
    private final ImageIcon boardIcon;
    private Tile tile;

    /**
     * @param message      Message to print when the obstacle is nearby
     * @param tile         Tile to place the obstacle on
     * @param obstacleType Type of obstacle for use in switch statements elsewhere
     * @param assetPath    path for image of obstacle
     * @throws TileOccupiedException If the specified tile already has something on it
     */
    protected Obstacle(String message, Tile tile, ObstacleType obstacleType, String assetPath) throws TileOccupiedException {
        if (!isUnoccupied(tile)) {
            throw new TileOccupiedException("Tile Occupied!");
        }
        this.tile = tile;
        this.obstacleType = obstacleType;
        this.message = message;
        this.boardIcon = Tile.loadImage(assetPath);
    }

    protected Obstacle(String message, Tile tile, ObstacleType obstacleType) throws TileOccupiedException {
        if (!isUnoccupied(tile)) {
            throw new TileOccupiedException("Tile Occupied!");
        }
        this.tile = tile;
        this.obstacleType = obstacleType;
        this.message = message;
        this.boardIcon = Tile.loadImage("data/cave_floor.png");
    }

    public Obstacle() { //TESTING ONLY, DO NOT IMPLEMENT IN CODE
        this.obstacleType = ObstacleType.WUMPUS;//test
        this.message = "test message";
        this.boardIcon = null;
    }

    /**
     * Checks if the supplied tile is empty
     *
     * @param tile Tile to check the state of
     * @return True if the tile is empty, false if something is on it
     */
    public static boolean isUnoccupied(Tile tile) {
        return tile.getContainedObstacle() == null;
    }

    /**
     * Gets the obstacle type on this tile
     *
     * @return ObstacleType, or null if there is no Obstacle Present
     */
    public ObstacleType getObstacleType() {
        return this.obstacleType;
    }

    /**
     * Gets the message for the obstacle occupying this space
     *
     * @return String message
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * @return the BoardLocation of the tile which this obstacle is on
     */
    public BoardLocation getLocation() {
        return tile.getBoardLocation();
    }

    /**
     * Sets the location of this obstacle to a new location
     *
     * @param tile new Tile which the obstacle is occupying
     */
    public void setLocation(Tile tile) {
        this.tile = tile;
    }

    /**
     * Gets the boardicon for this obstacle
     *
     * @return board icon for this obstacle type
     */
    public ImageIcon getBoardIcon() {
        return boardIcon;
    }

    /**
     * Types of Obstacle
     * {@link #WUMPUS}
     * {@link #PIT}
     * {@link #BATS}
     * {@link #TREASURE}
     * {@link #EXIT}
     * {@link #RAILGUN}
     * {@link #HUNTER_EYE}
     * {@link #HAWK_EYE}
     * {@link #BLIND_EYE}
     * {@link #SHIELD}
     * {@link #SAWMAN}
     * {@link #NONE}
     */
    public enum ObstacleType {
        /**
         * Represents the wumpus
         */
        WUMPUS,

        /**
         * Represents the Pit
         */
        PIT,

        /**
         * Represents the Bats
         */
        BATS,

        /**
         * Represents the Treasure
         */
        TREASURE,

        /**
         * Represents the Exit
         */
        EXIT,

        /**
         * Represents the ability to shoot 3 tiles in a direction set
         */
        RAILGUN,

        /**
         * Represents the ability to see all wumpus'
         */
        HUNTER_EYE,

        /**
         * Represents the ability to see all pits
         */
        HAWK_EYE,

        /**
         * Represents the ability lost to receive any warnings
         */
        BLIND_EYE,

        /**
         * Represents the ability where player gets a shield and can cheat death once
         */
        SHIELD,

        /**
         * Represents the ability where the power to shoot is lost
         */
        SAWMAN,

        /**
         * Represents an empty Obstacle
         */
        NONE
    }

    /**
     * Represents things which can happen when you encounter an obstacle
     */
    public enum OnEncounter {
        KILL,
        RANDOM_MOVE,
        MOVE,
        TREASURE_COLLECT,
        EXIT,
        RAILGUNPOWER,
        HAWKEYEPOWER,
        HUNTEREYEPOWER,
        SHIELDPOWER,
        SAWMANPOWER,
        BLINDPOWER,
        NOTHING
    }

}
