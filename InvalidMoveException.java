public class InvalidMoveException extends Exception {

    @SuppressWarnings("unused")
    public InvalidMoveException() {
    }

    public InvalidMoveException(String message) {
        super(message);
    }
}
