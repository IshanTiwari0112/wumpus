public class Wumpus extends Obstacle {
    public final static String WUMPUS_MESSAGE = "You smell a Wumpus";
    @SuppressWarnings("unused")
    public final static OnEncounter encounter = OnEncounter.KILL;
    private static final String assetPath = "data/wumpus.png";


    public Wumpus(Tile tile) throws TileOccupiedException {
        super(WUMPUS_MESSAGE, tile, ObstacleType.WUMPUS, assetPath);
    }
}
