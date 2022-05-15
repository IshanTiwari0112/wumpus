import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PlayerTest {

    Player player = new Player();

    @Test
    void getBoardLocation() {
        player.setBoardLocation(BoardLocation.of(3, 4));
        Assertions.assertEquals(player.getBoardLocation(), BoardLocation.of(3, 4));
    }

    @Test
    void setBoardLocation() {
        player.setBoardLocation(BoardLocation.of(3, 4));
        Assertions.assertEquals(player.getBoardLocation(), BoardLocation.of(3, 4));
    }

    @Test
    void isAlive() {
        Assertions.assertTrue(player.isAlive());
    }

    @Test
    void isTreasureCollected() {
        Assertions.assertFalse(player.isTreasureCollected());
    }

    @Test
    void isHunterPower() {
        Assertions.assertFalse(player.isHunterPower());
    }

    @Test
    void isHawkPower() {
        Assertions.assertFalse(player.isHawkPower());
    }

    @Test
    void isBlindPower() {
        Assertions.assertFalse(player.isBlindPower());
    }

    @Test
    void isShieldPower() {
        Assertions.assertFalse(player.isShieldPower());
    }

    @Test
    void isRailgunPower() {
        Assertions.assertFalse(player.isRailgunPower());
    }

    @Test
    void isSawmanPower() {
        Assertions.assertFalse(player.isSawmanPower());
    }

    @Test
    void setAlive() {
        player.setAlive(false);
        Assertions.assertFalse(player.isAlive());
    }

    @Test
    void setTreasureCollect() {
        player.setTreasureCollect(true);
        Assertions.assertTrue(player.isTreasureCollected());
    }

    @Test
    void setHunter() {
        player.setHunter(true);
        Assertions.assertTrue(player.isHunterPower());
    }

    @Test
    void setHawk() {
        player.setHawk(true);
        Assertions.assertTrue(player.isHawkPower());
    }

    @Test
    void setBlind() {
        player.setBlind(true);
        Assertions.assertTrue(player.isBlindPower());
    }

    @Test
    void setRailgun() {
        player.setRailgun(true);
        Assertions.assertTrue(player.isRailgunPower());
    }

    @Test
    void setShield() {
        player.setShield(true);
        Assertions.assertTrue(player.isShieldPower());
    }

    @Test
    void setSawman() {
        player.setSawman(true);
        Assertions.assertTrue(player.isSawmanPower());
    }
}