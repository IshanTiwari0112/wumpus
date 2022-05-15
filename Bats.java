public class Bats extends Obstacle {
    public static final String BAT_MESSAGE = "";
    @SuppressWarnings("unused")
    public static final OnEncounter onEncounter = OnEncounter.RANDOM_MOVE;
    private static final String assetPath = "data/cave_floor.png";

    public Bats(Tile tile) throws TileOccupiedException {
        super(BAT_MESSAGE, tile, ObstacleType.BATS, assetPath);
    }
}
