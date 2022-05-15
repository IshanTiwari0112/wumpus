import org.junit.jupiter.api.Assertions;

class BoardLocationTest {
    private final BoardLocation testLocation = new BoardLocation(3, 4);
    private final BoardLocation testLocation2 = new BoardLocation(3, 4);

    @org.junit.jupiter.api.Test
    void testEquals() {
        Assertions.assertEquals(testLocation, testLocation2);
    }

    @org.junit.jupiter.api.Test
    void of() {
        Assertions.assertEquals(testLocation, BoardLocation.of(3, 4));
    }

    @org.junit.jupiter.api.Test
    void fromAction() {
        Game.Action action = new Game.Action(Game.ActionType.MOVE, Game.ActionDirection.UP);
        Assertions.assertEquals(action.actionType, Game.ActionType.MOVE);
        Assertions.assertEquals(action.actionDirection, Game.ActionDirection.UP);
    }

    @org.junit.jupiter.api.Test
    void getRow() {
        Assertions.assertEquals(testLocation.getRow(), 3);
    }

    @org.junit.jupiter.api.Test
    void getColumn() {
        Assertions.assertEquals(testLocation.getColumn(), 4);
    }
}