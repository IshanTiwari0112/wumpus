public class Treasure extends Obstacle {
    public static final String TREASURE_MESSAGE = "shiny glitter";
    @SuppressWarnings("unused")
    public static final OnEncounter onEncounter = OnEncounter.TREASURE_COLLECT;
    private static final String assetPath = "data/cave_floor.png";

    public Treasure(Tile tile) throws TileOccupiedException {
        super(TREASURE_MESSAGE, tile, ObstacleType.TREASURE, assetPath);
    }
}
