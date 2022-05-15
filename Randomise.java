import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Class for the creation of random board locations or for a random wumpus location when it moves
 */
public class Randomise {

    /**
     * Creates a new boardlocation somewhere on the board
     * @param board board to create the location on
     * @param overlap true to overlap, false to avoid overlap
     * @return new Board Location
     */
    public static BoardLocation randomLocation(Board board, boolean overlap) {
        if(overlap) {
            return BoardLocation.of(ThreadLocalRandom.current()
                    .nextInt(0, board.getBoardHeight()), ThreadLocalRandom.current()
                    .nextInt(0, board.getBoardWidth()));
        }
        else {
            boolean done = false;
            BoardLocation boardLocation = null;
            while (!done) {
                boardLocation = BoardLocation.of(ThreadLocalRandom.current()
                        .nextInt(0, board.getBoardHeight()), ThreadLocalRandom.current()
                        .nextInt(0, board.getBoardWidth()));
                done = board.getTile(boardLocation).isEmpty();
            }
            return boardLocation;
        }
    }

    /**
     * Returns a location adjacent to the wumpus location for when it moves after the player shoots
     * @param oldLocation old location of the wumpus
     * @return new location for the wumpus
     */
    public static BoardLocation newWumpusLocation(BoardLocation oldLocation){
        Random r = new Random();
        switch(r.nextInt(3)+1){
            case 1 : {
                return BoardLocation.of(oldLocation.row() - 1, oldLocation.column());
            }
            case 2 : {
                return BoardLocation.of(oldLocation.row() + 1, oldLocation.column());
            }
            case 3 : {
                return BoardLocation.of(oldLocation.row(), oldLocation.column() - 1);
            }
            case 4 : {
                return BoardLocation.of(oldLocation.row(), oldLocation.column() + 1);
            }
            default : throw new RuntimeException();
        }

    }
}
