import java.awt.*;

public class Player {

    public static final String BOARD_REPRESENTATION = "@  ";
    private BoardLocation boardLocation;
    private boolean isAlive;
    private boolean treasureCollected;
    private boolean hunterPower;
    private boolean hawkPower;
    private boolean blindPower;
    private boolean shieldPower;
    private boolean railgunPower;
    private boolean sawmanPower;

    public Player() {
        this.isAlive = true;
        this.treasureCollected = false;
        this.hunterPower = false;
        this.hawkPower = false;
        this.blindPower = false;
        this.shieldPower = false;
        this.railgunPower = false;
        this.sawmanPower = false;
    }

    /**
     * Returns the BoardLocation of this player
     * @return BoardLocation of the player.
     */
    public BoardLocation getBoardLocation() {
        return boardLocation;
    }

    public void setBoardLocation(BoardLocation location) {
        this.boardLocation = location;
    }

    /**
     * @return Boolean stating if the player lives or not.
     */
    public boolean isAlive() {
        return isAlive;
    }

    public boolean isTreasureCollected() {
        return treasureCollected;
    }

    public boolean isHunterPower() {
        return hunterPower;
    }

    public boolean isHawkPower() {
        return hawkPower;
    }

    public boolean isBlindPower() {
        return blindPower;
    }

    public boolean isShieldPower() {
        return shieldPower;
    }

    public boolean isRailgunPower() {
        return railgunPower;
    }

    public boolean isSawmanPower() {
        return sawmanPower;
    }

    /**
     * Setter for isAlive attribute of player
     * @param alive Alive - true, Dead - false
     */
    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public void setTreasureCollect(boolean treasure){
        treasureCollected = treasure;
    }

    public void setHunter(boolean hunter) {
        hunterPower = hunter;
    }

    public void setHawk(boolean Hawk) {
        hawkPower = Hawk;
    }

    public void setBlind(boolean blind) {
        blindPower = blind;
    }

    public void setRailgun(boolean railgun) {
        railgunPower= railgun;
    }

    public void setShield(boolean shield) {
        shieldPower = shield;
    }

    public void setSawman(boolean sawman) {
        sawmanPower = sawman;
    }


}
//test