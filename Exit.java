public class Exit extends Obstacle {
    public static final String EXIT_MESSAGE = "";
    @SuppressWarnings("unused")
    public static final OnEncounter onEncounter = OnEncounter.EXIT;
    private final static String assetPath = "data/exit.png";

    public Exit(Tile tile) throws TileOccupiedException {
        super(EXIT_MESSAGE, tile, ObstacleType.EXIT, assetPath);
    }
}
