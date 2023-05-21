package hr.fer.ika.ikasus.config.security.dev;

import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
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
public class DevAuthManager implements AuthenticationManager {
    private final DevUserDetailsService detailsService;
    private final PasswordEncoder encoder;

    public DevAuthManager(DevUserDetailsService detailsService, PasswordEncoder encoder) {
        this.detailsService = detailsService;
        this.encoder = encoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authentication == null ||
                !(authentication.getCredentials() instanceof DevAuthenticationCredentials authCreds)
        ) {
            throw new BadCredentialsException("Invalid credential type");
        }

        this.detailsService.setLoginType(authCreds.getLoginType());

        DevUserDetails dud = (DevUserDetails) detailsService.loadUserByUsername((String)authentication.getPrincipal());

        if (dud == null) {
            throw new BadCredentialsException("Invalid user credentials");
        }

        if (!this.encoder.matches(authCreds.getPassword(), dud.getPassword())) {
            throw new BadCredentialsException("Invalid user credentials");
        }

        return new UsernamePasswordAuthenticationToken(dud, null, null);
    }
}
