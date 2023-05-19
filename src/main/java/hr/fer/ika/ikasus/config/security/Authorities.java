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

    public static DevUserDetails getDetails(Authentication auth) {
        if (notParsable(auth)) {
            return null;
        }

        return (DevUserDetails) auth.getPrincipal();
    }

    private static boolean notParsable(Authentication auth) {
        return auth == null || !(auth.getPrincipal() instanceof DevUserDetails);
    }

    public static boolean hasCustomerAuthority(Authentication auth) {
        DevUserDetails details = getDetails(auth);

        if (details == null) {
            return false;
        }

        return details.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(strAuthority -> strAuthority.equals(Authorities.CUSTOMER_AUTHORITY));
    }

    public static boolean hasManagerAuthority(Authentication auth) {
        DevUserDetails details = getDetails(auth);

        if (details == null) {
            return false;
        }

        return details.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(strAuthority -> strAuthority.equals(Authorities.MANAGER_AUTHORITY));
    }

    public static boolean hasEmployeeAuthority(Authentication auth) {
        DevUserDetails details = getDetails(auth);

        if (details == null) {
            return false;
        }

        return details.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(strAuthority -> strAuthority.equals(Authorities.EMPLOYEE_AUTHORITY));
    }

    public static boolean hasManagerOrEmployeeAuthority(Authentication auth) {
        return hasManagerAuthority(auth) || hasEmployeeAuthority(auth);
    }

    public static Integer extractSubjectId(Authentication auth) {
        DevUserDetails details = getDetails(auth);

        if (details != null) {
            return details.getId();
        }

        return null;
    }
}
