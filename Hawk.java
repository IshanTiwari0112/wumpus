public class Hawk extends Obstacle {

    public final static String HAWK_MESSAGE = "";
    @SuppressWarnings("unused")
    public final static OnEncounter encounter = OnEncounter.HAWKEYEPOWER;


    public Hawk(Tile tile) throws TileOccupiedException {
        super(HAWK_MESSAGE, tile, ObstacleType.HAWK_EYE);
    }
}
