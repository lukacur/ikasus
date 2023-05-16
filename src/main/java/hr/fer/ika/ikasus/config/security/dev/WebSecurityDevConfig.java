package hr.fer.ika.ikasus.config.security.dev;

import hr.fer.ika.ikasus.authorization.JWTRequestFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

/**
 * @author Luka Ćurić
 */
@EnableWebSecurity
@Configuration
public class WebSecurityDevConfig {
    private final JWTRequestFilter filter;

    public WebSecurityDevConfig(JWTRequestFilter filter) {
        this.filter = filter;
    }

    @Bean
    @Profile({ "dev" })
    public SecurityFilterChain buildSecurityFilter(HttpSecurity http) throws Exception {
        return http.
                csrf()
                    .disable()
                .formLogin()
                    .disable()
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(
                        (req, resp, ex) -> {
                            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
                        }
                )
                .and()
                .authorizeHttpRequests()
                    .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                    .requestMatchers("/home/**").permitAll()
                    .requestMatchers("/auth/**").permitAll()
                    .requestMatchers("/api/public/**").permitAll()
                    .requestMatchers("/api/authenticated/admin/**").hasAuthority("MANAGER")
                    .requestMatchers("/home/email-test").hasAuthority("MANAGER")
                    .requestMatchers("/api/authenticated/**").hasAnyAuthority("MANAGER", "EMPLOYEE")
                .and()
                .cors()
                .and()
                .build();
    }

    @Value("${ikasus.config.cors.origins.allow}")
    private String corsOrigins;

    @Bean
    @Profile({ "dev" })
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.stream(this.corsOrigins.split(";")).toList());
        configuration.setAllowedMethods(List.of("OPTIONS", "HEAD", "GET", "POST", "PATCH", "DELETE", "PUT"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
