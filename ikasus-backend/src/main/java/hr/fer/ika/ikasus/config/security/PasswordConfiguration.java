package hr.fer.ika.ikasus.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author Luka Ćurić
 */
@Configuration
public class PasswordConfiguration {
    @Bean
    public PasswordEncoder defaultEncoder() {
        return new BCryptPasswordEncoder(8);
    }
}
