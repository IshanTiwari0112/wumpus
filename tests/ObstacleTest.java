import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class ObstacleTest {

    private final Obstacle concreteObstacle = new Obstacle() {
        @Override
        public ObstacleType getObstacleType() {
            return ObstacleType.BATS;
        }

        @Override
        public String getMessage() {
            return "test String";
        }

        @Override
        public BoardLocation getLocation() {
            return super.getLocation();
        }

        @Override
        public void setLocation(Tile tile) {
            super.setLocation(tile);
        }


    };

    @Test
    void isUnoccupied() throws IOException {
        Assertions.assertTrue(Obstacle.isUnoccupied(new Tile(BoardLocation.of(3, 4))));
    }

    @Test
    void getObstacleType() throws IOException {
        Assertions.assertEquals(concreteObstacle.getObstacleType(), Obstacle.ObstacleType.BATS);
    }

    @Test
    void getMessage() {
        Assertions.assertEquals(concreteObstacle.getMessage(), "test String");
    }
}