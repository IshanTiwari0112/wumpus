public class Blind extends Obstacle {

    public final static String BLIND_MESSAGE = "";
    @SuppressWarnings("unused")
    public final static OnEncounter encounter = OnEncounter.BLINDPOWER;


    public Blind(Tile tile) throws TileOccupiedException {
        super(BLIND_MESSAGE, tile, ObstacleType.BLIND_EYE, "data/cave_floor.png");
    }
}
