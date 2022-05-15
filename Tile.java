import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Represents a tile on the board
 */
class Tile extends JLabel {
    public static final String TILE_REPRESENTATION = ".  ";
    private final BoardLocation boardLocation;
    ImageIcon withPlayer;
    ImageIcon bareTile;
    private Obstacle containedObstacle;
    private Player player;
    private boolean visible;

    /**
     * Constructor
     *
     * @param boardLocation location to place the tile at
     */
    protected Tile(BoardLocation boardLocation) throws IOException {
        this.boardLocation = boardLocation;
        this.bareTile = loadImage("data/cave_floor.png");
        this.withPlayer = loadImage("data/player_tile.png");
        this.visible = false;
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.setIcon(bareTile);
        this.setVisible(true);
        this.setPreferredSize(new Dimension(40, 40));
    }

    /**
     * Loads the image icon
     * @param path relative image path (relative to source root)
     * @return new image icon to set as the image for this tile
     */
    public static ImageIcon loadImage(String path) {
        try {
            BufferedImage original = ImageIO.read(new File(path));
            Image scaled = original.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            return new ImageIcon(scaled);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Obstacle getContainedObstacle() {
        return containedObstacle;
    }


    public void setContainedObstacle(Obstacle containedObstacle) {
        //exit should always be visible
        if (containedObstacle.getObstacleType() == Obstacle.ObstacleType.EXIT) {
            this.setIcon(containedObstacle.getBoardIcon());
        }
        //if correct powerup obtained wumpus and pit also visible
        if (visible) {
            if (containedObstacle.getObstacleType() == Obstacle.ObstacleType.WUMPUS || containedObstacle.getObstacleType() == Obstacle.ObstacleType.PIT)
                setIcon(containedObstacle.getBoardIcon());
        }
        this.containedObstacle = containedObstacle;
    }

    public BoardLocation getBoardLocation() {
        return boardLocation;
    }

    public boolean isEmpty() {
        return containedObstacle == null;
    }

    public boolean containsPlayer() {
        return player != null;
    }

    /**
     * Move the player to this tile
     *
     * @param player player to move
     */
    public void movePlayerHere(Player player) {
        this.player = player;
        this.setIcon(withPlayer);
        player.setBoardLocation(this.getBoardLocation());
    }

    public void removePlayer() {
        this.setIcon(bareTile);
        this.player = null;
    }

    
    @Deprecated
    //no need for this since graphics implemented
    public void changeTile(Obstacle obstacle1) {
        switch (obstacle1.getObstacleType()) {
            case WUMPUS:
                this.containedObstacle = obstacle1;
                this.setBackground(Color.PINK);
            case PIT:
                this.containedObstacle = obstacle1;
                this.setBackground(Color.CYAN);
            default:
                break;

        }
    }

    public void removeObstacle() {
        this.containedObstacle = null;
        if (visible) {
            setIcon(bareTile);
        }
    }

    public void toggleObstacleVisible() {
        this.visible = !this.visible;
        if (visible && !this.isEmpty()) {
            if (this.containedObstacle.getObstacleType() == Obstacle.ObstacleType.WUMPUS || this.containedObstacle.getObstacleType() == Obstacle.ObstacleType.PIT)
                updateIcon(this.getContainedObstacle().getBoardIcon());
        }
    }

    public boolean isObstacleVisible() {
        return this.visible;
    }

    public void updateIcon(ImageIcon icon) {
        this.setIcon(icon);
    }
}
