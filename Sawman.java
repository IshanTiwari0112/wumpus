public class Sawman extends Obstacle {

    public final static String SAWMAN_MESSAGE = "";
    @SuppressWarnings("unused")
    public final static OnEncounter encounter = OnEncounter.SAWMANPOWER;


    public Sawman(Tile tile) throws TileOccupiedException {
        super(SAWMAN_MESSAGE, tile, ObstacleType.SAWMAN, "data/cave_floor.png");
    }
}
