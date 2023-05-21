package hr.fer.ika.ikasus.service.implementation;

import hr.fer.ika.ikasus.DAO.Najam;
import hr.fer.ika.ikasus.DAO.Obavijest;
import hr.fer.ika.ikasus.DAO.ObavijestId;
import hr.fer.ika.ikasus.DAO.Vozilo;
import hr.fer.ika.ikasus.DTO.incoming.NotificationId;
import hr.fer.ika.ikasus.DTO.incoming.NotificationRequest;
import hr.fer.ika.ikasus.DTO.outgoing.NotificationResponse;
import hr.fer.ika.ikasus.repository.NajamRepository;
import hr.fer.ika.ikasus.repository.ObavijestRepository;
import hr.fer.ika.ikasus.service.NotificationService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Luka Ćurić
 */
@Service
public class NotificationServiceImpl implements NotificationService {
    private final NajamRepository najamRepository;
    private final ObavijestRepository obavijestRepository;

    private final Function<Obavijest, NotificationResponse> mapper = o -> {
        NotificationResponse notificationResponse = new NotificationResponse();
        ObavijestId idObj = o.getId();
        notificationResponse.setId(idObj.getIdobavijest());
        notificationResponse.setRentalId(idObj.getIdnajam());
        notificationResponse.setContent(o.getSadrzaj());
        notificationResponse.setSeen(o.getVidena());
        notificationResponse.setTime(Date.from(o.getVrijeme()));

        return notificationResponse;
    };

    public NotificationServiceImpl(NajamRepository najamRepository, ObavijestRepository obavijestRepository) {
        this.najamRepository = najamRepository;
        this.obavijestRepository = obavijestRepository;
    }

    @Override
    public List<NotificationResponse> getAllNotifications() {
        return this.obavijestRepository.findAll().stream()
                .map(this.mapper)
                .collect(Collectors.toList());
    }

    @Override
    public List<NotificationResponse> getAllNotificationsForRental(Integer rentalId) {
        if (rentalId == null) {
            return null;
        }

        Optional<Najam> rentalOpt = this.najamRepository.findById(rentalId);

        if (rentalOpt.isEmpty()) {
            return null;
        }

        return rentalOpt.get().getObavijests().stream()
                .filter(o -> !o.getObrisana())
                .map(this.mapper)
                .collect(Collectors.toList());
    }

    @Override
    public NotificationId createNotification(NotificationRequest notificationRequest) {
        if (notificationRequest == null || notificationRequest.getRentalId() == null) {
            return null;
        }

        Optional<Najam> rentOpt = this.najamRepository.findById(notificationRequest.getRentalId());

        if (rentOpt.isEmpty()) {
            return null;
        }

        ObavijestId notifId = new ObavijestId();
        Integer nextId = this.obavijestRepository.getNextIdForRental(notificationRequest.getRentalId());
        notifId.setIdobavijest((nextId == null) ? 1 : nextId);
        notifId.setIdnajam(notificationRequest.getRentalId());

        Obavijest notification = new Obavijest();
        notification.setId(notifId);
        notification.setIdnajam(rentOpt.get());

        notification.setSadrzaj(notificationRequest.getContent());

        notification.setVrijeme(Instant.now());
        if (notificationRequest.getTime() != null) {
            notification.setVrijeme(notificationRequest.getTime().toInstant());
        }

        notification.setVidena(false);
        notification.setObrisana(false);

        this.obavijestRepository.save(notification);

        notifId = notification.getId();

        NotificationId notificationId = new NotificationId();
        notificationId.setRentalId(notifId.getIdnajam());
        notificationId.setNotificationId(notifId.getIdobavijest());

        return notificationId;
    }

    @Override
    public List<NotificationResponse> getAllNotificationsForCustomer(Integer customerId) {
        return this.obavijestRepository.getForCustomer(customerId).stream()
                .filter(o -> !o.getObrisana())
                .map(this.mapper)
                .collect(Collectors.toList());
    }

    @Override
    public boolean markNotificationSeen(Integer customerId, Integer notificationId, Integer rentalId) {
        if (customerId == null || notificationId == null || rentalId == null) {
            return false;
        }

        ObavijestId notifId = new ObavijestId();
        notifId.setIdobavijest(notificationId);
        notifId.setIdnajam(rentalId);

        Optional<Obavijest> notifOpt = this.obavijestRepository.findById(notifId)
                .filter(o -> o.getIdnajam() != null && o.getIdnajam().getIdugovor() != null)
                .filter(o -> o.getIdnajam().getIdugovor().getIdzahtjev() != null)
                .filter(o -> o.getIdnajam().getIdugovor().getIdzahtjev().getIdunajmitelj() != null)
                .filter(o ->
                        Objects.equals(
                                o.getIdnajam().getIdugovor().getIdzahtjev().getIdunajmitelj().getId(), customerId
                        )
                );

        if (notifOpt.isEmpty()) {
            return false;
        }

        Obavijest notification = notifOpt.get();
        notification.setVidena(true);

        this.obavijestRepository.save(notification);

        return true;
    }

    @Override
    public boolean deleteNotification(Integer notificationId, Integer rentalId) {
        if (notificationId == null || rentalId == null) {
            return false;
        }

        ObavijestId notifId = new ObavijestId();
        notifId.setIdobavijest(notificationId);
        notifId.setIdnajam(rentalId);

        Optional<Obavijest> notifOpt = this.obavijestRepository.findById(notifId);

        if (notifOpt.isEmpty()) {
            return false;
        }

        this.obavijestRepository.delete(notifOpt.get());

        return true;
    }

    @Override
    public boolean markNotificationDeleted(Integer customerId, Integer notificationId, Integer rentalId) {
        if (customerId == null || notificationId == null || rentalId == null) {
            return false;
        }

        ObavijestId notifId = new ObavijestId();
        notifId.setIdobavijest(notificationId);
        notifId.setIdnajam(rentalId);

        Optional<Obavijest> notifOpt = this.obavijestRepository.findById(notifId)
                .filter(o -> o.getIdnajam() != null && o.getIdnajam().getIdugovor() != null)
                .filter(o -> o.getIdnajam().getIdugovor().getIdzahtjev() != null)
                .filter(o -> o.getIdnajam().getIdugovor().getIdzahtjev().getIdunajmitelj() != null)
                .filter(o ->
                        Objects.equals(
                                o.getIdnajam().getIdugovor().getIdzahtjev().getIdunajmitelj().getId(), customerId
                        )
                );

        if (notifOpt.isEmpty()) {
            return false;
        }

        Obavijest notification = notifOpt.get();
        notification.setObrisana(true);
        notification.setVidena(true);

        this.obavijestRepository.save(notification);

        return true;
    }

    private static final String MESSAGE_FORMAT = "Heads up! Your rental (Rental id: %d) of the vehicle %s will expire" +
            " in %d days.";
    private static final String EXPIRED_MESSAGE_FORMAT = "Your rental (Rental id: %d) has expired! Please return the " +
            "vehicle (%s).";

    @Override
    public void notifyExpiring() {
        this.najamRepository.findAll().stream()
                .filter(
                        rental -> rental != null && rental.getAktivan() && rental.getVrijemedo() != null &&
                                rental.getVrijemedo().until(Instant.now(), ChronoUnit.DAYS) <= 5
                )
                .filter(rental ->
                        !this.obavijestRepository.wasNotifiedRecently(
                                rental.getId(),
                                Instant.now().minus(3, ChronoUnit.DAYS)
                        )
                )
                .forEach(rental -> {
                    long daysUntil = rental.getVrijemedo().until(Instant.now(), ChronoUnit.DAYS);
                    Vozilo v = rental.getIdvozilo();

                    String text;

                    if (daysUntil <= 0) {
                        text = String.format(
                                EXPIRED_MESSAGE_FORMAT,
                                rental.getId(),
                                v.getNaziv()
                        );
                    } else {
                        text = String.format(
                                MESSAGE_FORMAT,
                                rental.getId(),
                                v.getNaziv(),
                                daysUntil
                        );
                    }

                    NotificationRequest notificationRequest = new NotificationRequest();
                    notificationRequest.setContent(text);
                    notificationRequest.setRentalId(rental.getId());
                    notificationRequest.setTime(new Date());

                    this.createNotification(notificationRequest);
                });
    }
}
