package hr.fer.ika.ikasus.controller;

import hr.fer.ika.ikasus.DTO.incoming.DeleteRequest;
import hr.fer.ika.ikasus.DTO.incoming.NotificationId;
import hr.fer.ika.ikasus.DTO.incoming.NotificationRequest;
import hr.fer.ika.ikasus.DTO.outgoing.CommonResponse;
import hr.fer.ika.ikasus.DTO.outgoing.NotificationResponse;
import hr.fer.ika.ikasus.config.security.Authorities;
import hr.fer.ika.ikasus.service.NotificationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

/**
 * @author Luka Ćurić
 */
@RestController
@RequestMapping(
        "/api/authenticated/notifications"
)
public class NotificationController {
    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping
    public ResponseEntity<List<NotificationResponse>> getNotifications(Authentication auth) {
        List<NotificationResponse> notifs;

        if (Authorities.hasCustomerAuthority(auth)) {
            notifs = this.notificationService.getAllNotificationsForCustomer(Authorities.extractSubjectId(auth));
        } else {
            notifs = this.notificationService.getAllNotifications();
        }

        return ResponseEntity.ok(notifs);
    }

    @PostMapping
    public ResponseEntity<NotificationId> createNotification(
            HttpServletRequest req,
            Authentication auth,
            @RequestBody NotificationRequest notificationRequest
    ) {
        if (!Authorities.hasManagerOrEmployeeAuthority(auth)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        NotificationId createdNotificationId = this.notificationService.createNotification(notificationRequest);

        if (createdNotificationId == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.created(URI.create(req.getServletPath())).body(createdNotificationId);
    }

    @PatchMapping
    public ResponseEntity<CommonResponse> setNotificationSeen(
            Authentication auth,
            @RequestBody NotificationId notificationId
    ) {
        if (!Authorities.hasCustomerAuthority(auth)) {
            return ResponseEntity.badRequest().build();
        }

        boolean seen = false;

        Integer customerId = Authorities.extractSubjectId(auth);
        seen = this.notificationService.markNotificationSeen(
                customerId, notificationId.getNotificationId(), notificationId.getRentalId()
        );

        if (!seen) {
            CommonResponse errResp = new CommonResponse();
            errResp.setIsError(true);
            errResp.setError("Invalid set notification seen request");

            return ResponseEntity.badRequest().body(errResp);
        }

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<CommonResponse> deleteNotification(
            Authentication auth,
            @RequestBody DeleteRequest<NotificationId> deleteRequest
    ) {
        boolean deleted = false;
        NotificationId notifId = deleteRequest.getId();

        if (Authorities.hasCustomerAuthority(auth)) {
            Integer customerId = Authorities.extractSubjectId(auth);
            deleted = this.notificationService.markNotificationDeleted(
                    customerId, notifId.getNotificationId(), notifId.getRentalId()
            );
        } else if (Authorities.hasManagerOrEmployeeAuthority(auth)) {
            deleted = this.notificationService.deleteNotification(notifId.getNotificationId(), notifId.getRentalId());
        }

        if (!deleted) {
            CommonResponse errResp = new CommonResponse();
            errResp.setIsError(true);
            errResp.setError("Invalid delete notification request");

            return ResponseEntity.badRequest().body(errResp);
        }

        return ResponseEntity.noContent().build();
    }
}
