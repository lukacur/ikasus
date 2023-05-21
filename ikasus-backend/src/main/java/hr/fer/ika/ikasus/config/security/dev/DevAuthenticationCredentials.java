package hr.fer.ika.ikasus.config.security.dev;

import java.util.Objects;

/**
 * @author Luka Ćurić
 */
public class DevAuthenticationCredentials {
    private final String password;
    private final LoginType loginType;

    public DevAuthenticationCredentials(String password, LoginType loginType) {
        this.password = Objects.requireNonNull(password);
        this.loginType = Objects.requireNonNull(loginType);
    }

    public String getPassword() {
        return password;
    }

    public LoginType getLoginType() {
        return loginType;
    }
}
