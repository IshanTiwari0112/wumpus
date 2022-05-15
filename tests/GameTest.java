import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class GameTest {

    Game testGame = new Game();

    @Test
    void getDirectionAsEnum() throws InvalidMoveException {
        Assertions.assertEquals(testGame.getDirectionAsEnum("up"), Game.ActionDirection.UP);
    }

    @Test
    void getTypeAsEnum() throws InvalidMoveException {
        Assertions.assertEquals(testGame.getTypeAsEnum("shoot"), Game.ActionType.SHOOT);
    }
}