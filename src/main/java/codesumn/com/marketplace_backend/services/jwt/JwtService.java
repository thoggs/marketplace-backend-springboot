package codesumn.com.marketplace_backend.services.jwt;

import codesumn.com.marketplace_backend.config.EnvironConfig;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

    private final String jwtSecret = EnvironConfig.JWT_SECRET;
    private final long jwtExpirationTime = EnvironConfig.JWT_EXPIRATION_TIME;

    public String generateToken(String username) {
        return JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + jwtExpirationTime))
                .sign(Algorithm.HMAC256(jwtSecret));
    }
}
