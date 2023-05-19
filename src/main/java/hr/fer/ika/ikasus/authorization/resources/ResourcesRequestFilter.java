package hr.fer.ika.ikasus.authorization.resources;

import hr.fer.ika.ikasus.config.security.Authorities;
import hr.fer.ika.ikasus.config.security.dev.DevUserDetails;
import hr.fer.ika.ikasus.resource.AppImage;
import hr.fer.ika.ikasus.service.ContractService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author Luka Ćurić
 */
@Component
public class ResourcesRequestFilter extends OncePerRequestFilter {
    private static final String PRIVATE_RESOURCES_ROOT = "/static-content/private";
    private static final String PRIVATE_IMAGES_ROOT = "/static-content/images/private";

    private final ContractService contractService;

    public ResourcesRequestFilter(ContractService contractService) {
        this.contractService = contractService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (Authorities.hasManagerOrEmployeeAuthority(auth)) {
            filterChain.doFilter(request, response);
            return;
        }

        if (auth == null || !(auth.getPrincipal() instanceof DevUserDetails)) {
            filterChain.doFilter(request, response);
            return;
        }

        if (!request.getServletPath().startsWith(PRIVATE_RESOURCES_ROOT) &&
                !request.getServletPath().startsWith(PRIVATE_IMAGES_ROOT)
        ) {
            filterChain.doFilter(request, response);
            return;
        }

        Integer customerId = Authorities.extractSubjectId(auth);

        if (customerId == null) {
            filterChain.doFilter(request, response);
            return;
        }

        String signaturePath = AppImage.STATIC_CONTENT_PREFIX + request.getServletPath();
        if (this.contractService.canViewSignature(customerId, signaturePath)) {
            AppImage.AppImageBuilder builder = AppImage.fromFileBuilder();

            AppImage image = builder.withImagePath(signaturePath).build();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image.getImageData(), "png", baos);

            response.setHeader("Content-Type", image.getContentType());
            response.getOutputStream().write(baos.toByteArray());
        } else {
            filterChain.doFilter(request, response);
        }
    }
}
