package hr.fer.ika.ikasus.authorization.jwt;

import hr.fer.ika.ikasus.config.security.dev.DevUserDetails;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

/**
 * @author Luka Ćurić
 */
@Component
public class JWTUtil {
    private static final long EXPIRE_DURATION = 12 * 60 * 60 * 1000;
    private static final String ISSUER = "IKA-ikasus";
    private static final Logger LOGGER = LoggerFactory.getLogger(JWTUtil.class);

    @Value("${ikasus.config.auth.jwt.secret}")
    private String SECRET;

    public enum DeclaredClaims {
        USER_TYPE("subjectType"),
        USER_AUTHORITY("userAuthority");

        private final String claimKey;

        DeclaredClaims(String claimKey) {
            this.claimKey = claimKey;
        }

        public String getClaimKey() {
            return claimKey;
        }
    }

    public String generateToken(DevUserDetails details) {

        SecretKeySpec spec = new SecretKeySpec(Base64.getDecoder().decode(this.SECRET), "HmacSHA256");

        Integer id = details.getId();
        String principal = details.getUsername();

        return Jwts.builder()
                .setSubject(String.format("%d,%s", id, principal))
                .setIssuer(ISSUER)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
                .addClaims(
                        Map.of(
                                DeclaredClaims.USER_TYPE.getClaimKey(), details.getType().getTypeId(),
                                DeclaredClaims.USER_AUTHORITY.getClaimKey(), details.serializeAuthorities()
                        )
                )
                .signWith(spec)
                .compact();
    }

    public boolean validateAccessToken(String token) {
        try {
            SecretKeySpec spec = new SecretKeySpec(Base64.getDecoder().decode(this.SECRET), "HmacSHA256");

            Jwts.parserBuilder()
                    .setSigningKey(spec)
                    .build()
                    .parseClaimsJws(token);

            return true;
        } catch (ExpiredJwtException ex) {
            LOGGER.error("JWT expired", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            LOGGER.error("Token is null, empty or only whitespace", ex.getMessage());
        } catch (MalformedJwtException ex) {
            LOGGER.error("JWT is invalid", ex);
        } catch (UnsupportedJwtException ex) {
            LOGGER.error("JWT is not supported", ex);
        } catch (SignatureException ex) {
            LOGGER.error("Signature validation failed");
        }

        return false;
    }

    public <T> T getClaim(String token, DeclaredClaims claim, Class<T> clazz) {
        Claims cl = this.parseClaims(token);

        return cl.get(claim.getClaimKey(), clazz);
    }

    public String getSubject(String token) {
        return parseClaims(token).getSubject();
    }

    private Claims parseClaims(String token) {
        SecretKeySpec spec = new SecretKeySpec(this.SECRET.getBytes(StandardCharsets.UTF_8), "HmacSHA256");

        return Jwts.parserBuilder()
                .setSigningKey(spec)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
