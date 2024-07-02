package codesumn.com.marketplace_backend.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class EnvironConfig {
    public static final String JWT_SECRET;
    public static final long JWT_EXPIRATION_TIME;
    public static final String GITHUB_API_URL;

    static {
        Dotenv dotenv = Dotenv.load();
        JWT_SECRET = dotenv.get("JWT_SECRET");
        JWT_EXPIRATION_TIME = Long.parseLong(Objects.requireNonNull(dotenv.get("JWT_EXPIRATION_TIME")));
        GITHUB_API_URL = dotenv.get("GITHUB_API_URL");
    }
}
