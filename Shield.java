public class Shield extends Obstacle {

    public final static String SHIELD_MESSAGE = "";
    @SuppressWarnings("unused")
    public final static OnEncounter encounter = OnEncounter.SHIELDPOWER;


    public Shield(Tile tile) throws TileOccupiedException {
        super(SHIELD_MESSAGE, tile, ObstacleType.SHIELD);
    }
}
