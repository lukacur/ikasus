package hr.fer.ika.ikasus.service;

import hr.fer.ika.ikasus.DTO.incoming.NotificationId;
import hr.fer.ika.ikasus.DTO.incoming.NotificationRequest;
import hr.fer.ika.ikasus.DTO.outgoing.NotificationResponse;

import java.util.List;

/**
 * @author Luka Ćurić
 */
public interface NotificationService {
    List<NotificationResponse> getAllNotifications();
    List<NotificationResponse> getAllNotificationsForRental(Integer rentalId);
    List<NotificationResponse> getAllNotificationsForCustomer(Integer customerId);

    boolean markNotificationSeen(Integer customerId, Integer notificationId, Integer rentalId);

    NotificationId createNotification(NotificationRequest notificationRequest);

    boolean deleteNotification(Integer notificationId, Integer rentalId);
    boolean markNotificationDeleted(Integer customerId, Integer notificationId, Integer rentalId);
}
