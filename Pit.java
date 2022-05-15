import java.awt.*;
public class Pit extends Obstacle {
    public final static String PIT_MESSAGE = "You feel a breeze";
    @SuppressWarnings("unused")
    public final static OnEncounter onEncounter = OnEncounter.KILL;
    private static final String assetPath = "data/pit.png";


    public Pit(Tile tile) throws TileOccupiedException {
        super(PIT_MESSAGE, tile, ObstacleType.PIT, assetPath);
    }

    public static void tileColourChange(Tile tile ){
        tile.setBackground(Color.BLUE);

    }
}
