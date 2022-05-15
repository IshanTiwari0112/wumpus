import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class TileTest {

    private final Tile testTile = new Tile(BoardLocation.of(3, 4));

    TileTest() throws IOException {
    }

    @Test
    void getContainedObstacle() {
        Assertions.assertNull(testTile.getContainedObstacle());
    }

    @Test
    void setContainedObstacle() throws TileOccupiedException {
        testTile.setContainedObstacle(new Wumpus(testTile));
        Assertions.assertEquals(testTile.getContainedObstacle().getObstacleType(), Obstacle.ObstacleType.WUMPUS);
    }

    @Test
    void getBoardLocation() {
        Assertions.assertEquals(testTile.getBoardLocation(), BoardLocation.of(3, 4));
    }

    @Test
    void isEmpty() {
        Assertions.assertTrue(testTile.isEmpty());
    }

    @Test
    void containsPlayer() {
        testTile.movePlayerHere(new Player());
        Assertions.assertTrue(testTile.containsPlayer());
    }

    @Test
    void movePlayerHere() {
        //tested in above test
    }

    @Test
    void removePlayer() {
        testTile.movePlayerHere(new Player());
        Assertions.assertTrue(testTile.containsPlayer());
        testTile.removePlayer();
        Assertions.assertFalse(testTile.containsPlayer());
    }

    @Test
    void removeObstacle() throws TileOccupiedException {
        testTile.setContainedObstacle(new Wumpus(testTile));
        Assertions.assertSame(testTile.getContainedObstacle().getObstacleType(), Obstacle.ObstacleType.WUMPUS);
        testTile.removeObstacle();
        Assertions.assertNull(testTile.getContainedObstacle());
    }
}