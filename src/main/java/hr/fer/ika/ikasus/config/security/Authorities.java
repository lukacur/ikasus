package hr.fer.ika.ikasus.config.security;

import hr.fer.ika.ikasus.config.security.dev.DevUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

/**
 * @author Luka Ćurić
 */
public class Authorities {
    public static final String CUSTOMER_AUTHORITY = "CUSTOMER";
    public static final String MANAGER_AUTHORITY = "MANAGER";
    public static final String EMPLOYEE_AUTHORITY = "EMPLOYEE";

    private Authorities() {}

    public static boolean isParsable(Authentication auth) {
        return auth != null && auth.getPrincipal() instanceof DevUserDetails;
    }

    public static boolean hasCustomerAuthority(Authentication auth) {
        if (auth == null || !(auth.getPrincipal() instanceof DevUserDetails details)) {
            return false;
        }

        return details.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(strAuthority -> strAuthority.equals(Authorities.CUSTOMER_AUTHORITY));
    }

    public static boolean hasManagerAuthority(Authentication auth) {
        if (auth == null || !(auth.getPrincipal() instanceof DevUserDetails details)) {
            return false;
        }

        return details.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(strAuthority -> strAuthority.equals(Authorities.MANAGER_AUTHORITY));
    }

    public static boolean hasEmployeeAuthority(Authentication auth) {
        if (auth == null || !(auth.getPrincipal() instanceof DevUserDetails details)) {
            return false;
        }

        return details.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(strAuthority -> strAuthority.equals(Authorities.EMPLOYEE_AUTHORITY));
    }

    public static Integer extractSubjectId(Authentication auth) {
        if (!isParsable(auth)) {
            return null;
        }

        if (auth.getPrincipal() instanceof DevUserDetails details) {
            return details.getId();
        }

        return null;
    }
}
