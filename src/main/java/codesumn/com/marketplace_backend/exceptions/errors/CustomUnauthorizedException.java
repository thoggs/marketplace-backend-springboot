package codesumn.com.marketplace_backend.exceptions.errors;

public class CustomUnauthorizedException extends RuntimeException {
    public CustomUnauthorizedException(String message) {
        super(message);
    }
}
