package hr.fer.ika.ikasus.authorization;

import hr.fer.ika.ikasus.authorization.jwt.JWTUtil;
import hr.fer.ika.ikasus.config.security.dev.SubjectType;
import hr.fer.ika.ikasus.config.security.dev.DevUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * @author Luka Ćurić
 */
@Component
public class JWTRequestFilter extends OncePerRequestFilter {
    private static final String TOKEN_HEADER = "Authorization";

    private final JWTUtil jwtUtil;

    public JWTRequestFilter(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    private static boolean bearerHeaderPresent(HttpServletRequest req) {
        String headerValue = req.getHeader(TOKEN_HEADER);

        if (headerValue == null) {
            return false;
        }

        return !headerValue.isBlank() && headerValue.startsWith("Bearer");
    }

    private static String extractToken(HttpServletRequest req) {
        return req.getHeader(TOKEN_HEADER).split(" ")[1].trim();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!bearerHeaderPresent(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = extractToken(request);

        if (!this.jwtUtil.validateAccessToken(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        String subjectStr;
        int id;
        String principal;
        Integer type;
        String authorities;

        try {
            subjectStr = this.jwtUtil.getSubject(token);
            String[] parts = subjectStr.split(",");
            id = Integer.parseInt(parts[0]);
            principal = parts[1];
            type = this.jwtUtil.getClaim(token, JWTUtil.DeclaredClaims.USER_TYPE, Integer.class);
            authorities = this.jwtUtil.getClaim(token, JWTUtil.DeclaredClaims.USER_AUTHORITY, String.class);
        } catch (Exception e) {
            filterChain.doFilter(request, response);
            return;
        }

        DevUserDetails userDetails = DevUserDetails.builder()
               .withId(id)
               .withUsername(principal)
               .withType(SubjectType.getType(type))
               .withPassword("")
               .withAuthoritiesDeserialized(authorities)
               .build();

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authToken);


        filterChain.doFilter(request, response);
    }
}
