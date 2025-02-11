package hotel.com.controller.exception;

public class IllegalArgumentException  extends RuntimeException {
    public IllegalArgumentException(String message) {
        super(message);
    }
}