public class Hunter extends Obstacle {

    public final static String HUNTER_MESSAGE = "";
    @SuppressWarnings("unused")
    public final static OnEncounter encounter = OnEncounter.HUNTEREYEPOWER;


    public Hunter(Tile tile) throws TileOccupiedException {
        super(HUNTER_MESSAGE, tile, ObstacleType.HUNTER_EYE, "data/cave_floor.png");
    }
}
