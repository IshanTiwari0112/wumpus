import java.util.Objects;

/**
 * Record class for storing BoardLocation, however records are not supported in
 * Java 11, so I made it final instead.
 */
public final class BoardLocation {
    private final int row;
    private final int column;

    BoardLocation(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int row() {
        return row;
    }

    public int column() {
        return column;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (BoardLocation) obj;
        return this.row == that.row &&
                this.column == that.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }

    @Override
    public String toString() {
        return "BoardLocation[" +
                "row=" + row + ", " +
                "column=" + column + ']';
    }

    /**
     * Static method for generating a BoardLocation from coordinates
     * @param row row
     * @param column column
     * @return new BoardLocation with those coordinates
     */
    public static BoardLocation of(int row, int column) {
        return new BoardLocation(row, column);
    }

    /**
     * Gets a new BoardLocation from an Action direction
     * @param oldLocation location the thing was at before
     * @param action the action the thing did
     * @return new BoardLocation incremented in the direction of the action
     */
    public static BoardLocation fromAction(BoardLocation oldLocation, Game.Action action) {
        switch (action.actionDirection) {

            case UP : {
                return BoardLocation.of(oldLocation.row - 1, oldLocation.column);
            }
            case DOWN : {
                return BoardLocation.of(oldLocation.row + 1, oldLocation.column);
            }
            case LEFT : {
                return BoardLocation.of(oldLocation.row, oldLocation.column - 1);
            }
            case RIGHT : {
                return BoardLocation.of(oldLocation.row, oldLocation.column + 1);
            }
            default : throw new RuntimeException();
        }
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
}
