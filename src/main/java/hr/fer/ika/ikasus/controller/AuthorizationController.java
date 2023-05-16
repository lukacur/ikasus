package hr.fer.ika.ikasus.controller;

import hr.fer.ika.ikasus.DTO.incoming.AuthRequest;
import hr.fer.ika.ikasus.DTO.outgoing.AuthResponse;
import hr.fer.ika.ikasus.authorization.jwt.JWTUtil;
import hr.fer.ika.ikasus.config.security.dev.DevUserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Luka Ćurić
 */
@RestController
@RequestMapping(
        value = "/auth",
        consumes = { MediaType.APPLICATION_JSON_VALUE },
        produces = { MediaType.APPLICATION_JSON_VALUE }
)
public class AuthorizationController {
    private final AuthenticationManager authManager;
    private final JWTUtil jwtUtil;

    public AuthorizationController(AuthenticationManager authManager, JWTUtil jwtUtil) {
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
    }

    private ResponseEntity<AuthResponse> authenticateUser(AuthRequest req) {
        try {
            Authentication auth = this.authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(req.getPrincipal(), req.getCredentials())
            );

            if (auth.getPrincipal() instanceof DevUserDetails details) {
                AuthResponse resp = new AuthResponse();
                resp.setToken(this.jwtUtil.generateToken(details));

                return ResponseEntity.ok(resp);
            }

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (BadCredentialsException bce) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/manager")
    public ResponseEntity<AuthResponse> authenticateManager(@RequestBody AuthRequest req) {
        return this.authenticateUser(req);
    }

    @PostMapping("/employee")
    public ResponseEntity<AuthResponse> authenticateEmployee(@RequestBody AuthRequest req) {
        return this.authenticateUser(req);
    }

    @PostMapping("/customer")
    public ResponseEntity<AuthResponse> authenticateCustomer(@RequestBody AuthRequest req) {
        return this.authenticateUser(req);
    }
}
