package hr.fer.ika.ikasus.config.security.dev;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author Luka Ćurić
 */
public class DevUserDetails implements UserDetails {
    public static class DevUserDetailsBuilder {
        private Integer id;
        private String username;
        private String password;
        private SubjectType type;
        private Collection<? extends GrantedAuthority> authorities;

        private DevUserDetailsBuilder() {}

        public DevUserDetailsBuilder withId(Integer id) {
            this.id = id;
            return this;
        }

        public DevUserDetailsBuilder withUsername(String username) {
            this.username = username;
            return this;
        }

        public DevUserDetailsBuilder withPassword(String password) {
            this.password = password;
            return this;
        }

        public DevUserDetailsBuilder withType(SubjectType type) {
            this.type = type;
            return this;
        }

        public DevUserDetailsBuilder withAuthorities(Collection<? extends GrantedAuthority> authorities) {
            this.authorities = authorities;
            return this;
        }

        public DevUserDetailsBuilder withAuthoritiesDeserialized(String authoritiesString) {
            this.authorities = Arrays.stream(authoritiesString.split(";"))
                    .map(a -> (GrantedAuthority)() -> a).collect(Collectors.toList());
            return this;
        }

        public DevUserDetails build() {
            if (this.id == null || this.username == null || this.password == null || this.type == null ||
                    this.authorities == null
            ) {
                throw new IllegalStateException("All fields must be set");
            }

            return new DevUserDetails(
                    this.id,
                    this.username,
                    this.password,
                    this.type,
                    this.authorities
            );
        }
    }

    private final Integer id;
    private final String username;
    private final String password;
    private final SubjectType type;
    private final Collection<? extends GrantedAuthority> authorities;

    private DevUserDetails(
            Integer id,
            String username,
            String password,
            SubjectType type,
            Collection<? extends GrantedAuthority> authorities
    ) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.type = type;
        this.authorities = authorities;
    }

    public static DevUserDetailsBuilder builder() {
        return new DevUserDetailsBuilder();
    }

    public Integer getId() {
        return id;
    }

    public SubjectType getType() {
        return type;
    }

    public String serializeAuthorities() {
        return this.authorities.stream().map(GrantedAuthority::getAuthority).reduce((acc, str) -> acc + ";" + str)
                .orElse("");
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
