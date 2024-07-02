package codesumn.com.marketplace_backend.security.auth;

public class CustomUserNotFoundException extends RuntimeException {
    public CustomUserNotFoundException() {
        super();
    }

    public CustomUserNotFoundException(String message) {
        super(message);
    }
}
