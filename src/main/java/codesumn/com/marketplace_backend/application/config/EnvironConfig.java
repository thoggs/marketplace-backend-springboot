package codesumn.com.marketplace_backend.application.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class EnvironConfig {
    public static final String JWT_SECRET;
    public static final long JWT_EXPIRATION_TIME;
    public static final String GITHUB_API_URL;
    public static final String SEED_DB;
    public static final String GITHUB_USER_PATH;
    public static final String GITHUB_SCHEME;

    static {
        Dotenv dotenv = Dotenv
                .configure()
                .ignoreIfMissing()
                .load();

        JWT_SECRET = System.getenv("JWT_SECRET") != null
                ? System.getenv("JWT_SECRET")
                : dotenv.get("JWT_SECRET");

        JWT_EXPIRATION_TIME = System.getenv("JWT_EXPIRATION_TIME") != null
                ? Long.parseLong(System.getenv("JWT_EXPIRATION_TIME"))
                : Long.parseLong(Objects.requireNonNull(dotenv.get("JWT_EXPIRATION_TIME")));

        GITHUB_API_URL = System.getenv("GITHUB_API_URL") != null
                ? System.getenv("GITHUB_API_URL")
                : dotenv.get("GITHUB_API_URL");

        GITHUB_USER_PATH = System.getenv("GITHUB_USER_PATH") != null
                ? System.getenv("GITHUB_USER_PATH")
                : dotenv.get("GITHUB_USER_PATH");

        GITHUB_SCHEME = System.getenv("GITHUB_SCHEME") != null
                ? System.getenv("GITHUB_SCHEME")
                : dotenv.get("GITHUB_SCHEME");

        SEED_DB = System.getenv("SEED_DB") != null
                ? System.getenv("SEED_DB")
                : dotenv.get("SEED_DB");
    }
}
