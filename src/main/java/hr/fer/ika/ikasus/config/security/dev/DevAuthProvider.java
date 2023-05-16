package hr.fer.ika.ikasus.config.security.dev;

import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author Luka Ćurić
 */
@Component
@Profile({ "dev" })
public class DevAuthProvider implements AuthenticationManager {
    private final DevUserDetailsService detailsService;
    private final PasswordEncoder encoder;

    public DevAuthProvider(DevUserDetailsService detailsService, PasswordEncoder encoder) {
        this.detailsService = detailsService;
        this.encoder = encoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        DevUserDetails dud = (DevUserDetails) detailsService.loadUserByUsername((String)authentication.getPrincipal());

        if (this.encoder.matches((String)authentication.getCredentials(), dud.getPassword())) {
            return new UsernamePasswordAuthenticationToken(dud, null, null);
        }

        return null;
    }
}
