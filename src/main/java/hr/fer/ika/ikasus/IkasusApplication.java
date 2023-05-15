package hr.fer.ika.ikasus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication(exclude = { org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class })
public class IkasusApplication {

	public static void main(String[] args) {
		SpringApplication.run(IkasusApplication.class, args);
	}

}
