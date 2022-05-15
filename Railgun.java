public class Railgun extends Obstacle {

    public final static String RAILGUN_MESSAGE = "";
    @SuppressWarnings("unused")
    public final static OnEncounter encounter = OnEncounter.RAILGUNPOWER;


    public Railgun(Tile tile) throws TileOccupiedException {
        super(RAILGUN_MESSAGE, tile, ObstacleType.RAILGUN);
    }
}
