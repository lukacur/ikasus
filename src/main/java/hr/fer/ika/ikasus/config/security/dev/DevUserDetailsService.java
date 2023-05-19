package hr.fer.ika.ikasus.config.security.dev;

import hr.fer.ika.ikasus.DAO.Iznajmljivac;
import hr.fer.ika.ikasus.DAO.Unajmitelj;
import hr.fer.ika.ikasus.DAO.UpraviteljPoslovnice;
import hr.fer.ika.ikasus.config.security.Authorities;
import hr.fer.ika.ikasus.repository.IznajmljivacRepository;
import hr.fer.ika.ikasus.repository.UnajmiteljRepository;
import hr.fer.ika.ikasus.repository.UpraviteljPoslovniceRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * @author Luka Ćurić
 */
@Component
@Profile({ "dev" })
public class DevUserDetailsService implements UserDetailsService {
    private final IznajmljivacRepository iznajmljivacRepository;
    private final UpraviteljPoslovniceRepository upraviteljPoslovniceRepository;
    private final UnajmiteljRepository unajmiteljRepository;

    private LoginType loginType;

    public DevUserDetailsService(
            IznajmljivacRepository iznajmljivacRepository,
            UpraviteljPoslovniceRepository upraviteljPoslovniceRepository,
            UnajmiteljRepository unajmiteljRepository
    ) {
        this.iznajmljivacRepository = iznajmljivacRepository;
        this.upraviteljPoslovniceRepository = upraviteljPoslovniceRepository;
        this.unajmiteljRepository = unajmiteljRepository;
    }

    public void setLoginType(LoginType loginType) {
        this.loginType = loginType;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (this.loginType == null) {
            return null;
        }

        DevUserDetails details = null;
        DevUserDetails.DevUserDetailsBuilder builder = DevUserDetails.builder();

        switch (this.loginType) {
            case MANAGER -> {
                Optional<UpraviteljPoslovnice> up = this.upraviteljPoslovniceRepository.findByKorisnickoime(username);

                if (up.isPresent()) {
                    UpraviteljPoslovnice upraviteljPoslovnice = up.get();

                    details = builder.withId(upraviteljPoslovnice.getId())
                            .withUsername(upraviteljPoslovnice.getKorisnickoime())
                            .withPassword(upraviteljPoslovnice.getLozinka())
                            .withType(SubjectType.MANAGER)
                            .withAuthorities(List.of(() -> Authorities.MANAGER_AUTHORITY))
                            .build();
                }
            }

            case CUSTOMER -> {
                Optional<Iznajmljivac> izn = this.iznajmljivacRepository.findByEmail(username);

                if (izn.isPresent()) {
                    Iznajmljivac iznajmljivac = izn.get();
                    details = builder.withId(iznajmljivac.getId())
                            .withUsername(iznajmljivac.getEmail())
                            .withPassword(iznajmljivac.getLozinka())
                            .withType(SubjectType.EMPLOYEE)
                            .withAuthorities(List.of(() -> Authorities.EMPLOYEE_AUTHORITY))
                            .build();
                }
            }

            case EMPLOYEE -> {
                Optional<Unajmitelj> unj = this.unajmiteljRepository.findByEmail(username);

                if (unj.isPresent()) {
                    Unajmitelj unajmitelj = unj.get();
                    details = builder.withId(unajmitelj.getId())
                            .withUsername(unajmitelj.getEmail())
                            .withPassword(unajmitelj.getLozinka())
                            .withType(SubjectType.CUSTOMER)
                            .withAuthorities(List.of(() -> Authorities.CUSTOMER_AUTHORITY))
                            .build();
                }
            }
        }

        return details;
    }
}
