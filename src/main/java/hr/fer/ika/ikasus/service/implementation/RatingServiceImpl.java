package hr.fer.ika.ikasus.service.implementation;

import hr.fer.ika.ikasus.DAO.Recenzija;
import hr.fer.ika.ikasus.DAO.Unajmitelj;
import hr.fer.ika.ikasus.DAO.Vozilo;
import hr.fer.ika.ikasus.DTO.incoming.CreateRatingInfo;
import hr.fer.ika.ikasus.DTO.incoming.UpdateRatingInfo;
import hr.fer.ika.ikasus.DTO.outgoing.RatingInfo;
import hr.fer.ika.ikasus.repository.RecenzijaRepository;
import hr.fer.ika.ikasus.repository.UnajmiteljRepository;
import hr.fer.ika.ikasus.repository.VoziloRepository;
import hr.fer.ika.ikasus.service.RatingService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Luka Ćurić
 */
@Service
public class RatingServiceImpl implements RatingService {
    private final RecenzijaRepository recenzijaRepository;
    private final VoziloRepository voziloRepository;
    private final UnajmiteljRepository unajmiteljRepository;

    public RatingServiceImpl(
            RecenzijaRepository recenzijaRepository,
            VoziloRepository voziloRepository,
            UnajmiteljRepository unajmiteljRepository
    ) {
        this.recenzijaRepository = recenzijaRepository;
        this.voziloRepository = voziloRepository;
        this.unajmiteljRepository = unajmiteljRepository;
    }

    @Override
    public List<RatingInfo> getAllRatings() {
        return this.recenzijaRepository.findAll().stream()
                .map(r -> {
                    Unajmitelj c = r.getIdunajmitelj();
                    Vozilo v = r.getIdvozilo();

                    RatingInfo ratingInfo = new RatingInfo();
                    ratingInfo.setId(r.getId());

                    ratingInfo.setVehicleId(v.getId());
                    ratingInfo.setVehicleName(v.getNaziv());

                    ratingInfo.setCustomerId(c.getId());
                    ratingInfo.setCustomerFullName(c.getIme() + " " + c.getPrezime());

                    ratingInfo.setContent(r.getSadrzaj());
                    ratingInfo.setRating(r.getOcjena());
                    ratingInfo.setKmDriven(r.getKilometraza());
                    ratingInfo.setTime(Date.from(r.getVrijeme()));

                    return ratingInfo;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<RatingInfo> getRatingsForVehicle(Integer vehicleId) {
        if (vehicleId == null) {
            return null;
        }

        Optional<Vozilo> vehOpt = this.voziloRepository.findById(vehicleId);

        if (vehOpt.isEmpty()) {
            return null;
        }

        return vehOpt.get().getRecenzijas().stream()
                .map(r -> {
                    Unajmitelj c = r.getIdunajmitelj();
                    Vozilo v = r.getIdvozilo();

                    RatingInfo ratingInfo = new RatingInfo();
                    ratingInfo.setId(r.getId());

                    ratingInfo.setVehicleId(v.getId());
                    ratingInfo.setVehicleName(v.getNaziv());

                    ratingInfo.setCustomerId(c.getId());
                    ratingInfo.setCustomerFullName(c.getIme() + " " + c.getPrezime());

                    ratingInfo.setContent(r.getSadrzaj());
                    ratingInfo.setRating(r.getOcjena());
                    ratingInfo.setKmDriven(r.getKilometraza());
                    ratingInfo.setTime(Date.from(r.getVrijeme()));

                    return ratingInfo;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<RatingInfo> getRatingsByCustomer(Integer customerId) {
        if (customerId == null) {
            return null;
        }

        Optional<Unajmitelj> custOpt = this.unajmiteljRepository.findById(customerId);

        if (custOpt.isEmpty()) {
            return null;
        }

        return custOpt.get().getRecenzijas().stream()
                .map(r -> {
                    Unajmitelj c = r.getIdunajmitelj();
                    Vozilo v = r.getIdvozilo();

                    RatingInfo ratingInfo = new RatingInfo();
                    ratingInfo.setId(r.getId());

                    ratingInfo.setVehicleId(v.getId());
                    ratingInfo.setVehicleName(v.getNaziv());

                    ratingInfo.setCustomerId(c.getId());
                    ratingInfo.setCustomerFullName(c.getIme() + " " + c.getPrezime());

                    ratingInfo.setContent(r.getSadrzaj());
                    ratingInfo.setRating(r.getOcjena());
                    ratingInfo.setKmDriven(r.getKilometraza());
                    ratingInfo.setTime(Date.from(r.getVrijeme()));

                    return ratingInfo;
                })
                .collect(Collectors.toList());
    }

    @Override
    public RatingInfo getRating(Integer ratingId) {
        if (ratingId == null) {
            return null;
        }

        Optional<Recenzija> ratOpt = this.recenzijaRepository.findById(ratingId);

        if (ratOpt.isEmpty()) {
            return null;
        }

        return ratOpt.map(r -> {
            Unajmitelj c = r.getIdunajmitelj();
            Vozilo v = r.getIdvozilo();

            RatingInfo ratingInfo = new RatingInfo();
            ratingInfo.setId(r.getId());

            ratingInfo.setVehicleId(v.getId());
            ratingInfo.setVehicleName(v.getNaziv());

            ratingInfo.setCustomerId(c.getId());
            ratingInfo.setCustomerFullName(c.getIme() + " " + c.getPrezime());

            ratingInfo.setContent(r.getSadrzaj());
            ratingInfo.setRating(r.getOcjena());
            ratingInfo.setKmDriven(r.getKilometraza());
            ratingInfo.setTime(Date.from(r.getVrijeme()));

            return ratingInfo;
        }).get();
    }

    @Override
    public boolean updateRating(Integer customerId, Integer ratingId, UpdateRatingInfo updateRatingInfo) {
        if (customerId == null || ratingId == null) {
            return false;
        }

        Optional<Recenzija> ratOpt = this.recenzijaRepository.findById(ratingId)
                .filter(r -> Objects.equals(r.getIdunajmitelj().getId(), customerId));

        if (ratOpt.isEmpty()) {
            return false;
        }

        Recenzija rating = ratOpt.get();

        if (updateRatingInfo.getRating() != null) {
            rating.setOcjena(updateRatingInfo.getRating());
        }

        if (updateRatingInfo.getContent() != null) {
            rating.setSadrzaj(updateRatingInfo.getContent());
        }

        this.recenzijaRepository.save(rating);

        return true;
    }

    @Override
    public Integer createRating(Integer customerId, CreateRatingInfo createRatingInfo) {
        if (createRatingInfo.getVehicleId() == null || customerId == null) {
            return null;
        }

        Optional<Vozilo> vehOpt = this.voziloRepository.findById(createRatingInfo.getVehicleId());
        Optional<Unajmitelj> custOpt = this.unajmiteljRepository.findById(customerId);

        if (vehOpt.isEmpty() || custOpt.isEmpty()) {
            return null;
        }

        Recenzija rating = new Recenzija();
        rating.setIdvozilo(vehOpt.get());
        rating.setIdunajmitelj(custOpt.get());
        rating.setSadrzaj(createRatingInfo.getContent());
        rating.setOcjena(createRatingInfo.getRating());
        rating.setKilometraza(createRatingInfo.getKmDriven());
        rating.setVrijeme(new Date().toInstant());

        rating = this.recenzijaRepository.save(rating);

        return rating.getId();
    }

    @Override
    public boolean deleteRating(Integer customerId, Integer ratingId) {
        if (ratingId == null) {
            return false;
        }

        Optional<Recenzija> ratOpt = this.recenzijaRepository.findById(ratingId)
                .filter(r -> customerId == null || Objects.equals(r.getIdunajmitelj().getId(), customerId));

        if (ratOpt.isEmpty()) {
            return false;
        }

        this.recenzijaRepository.delete(ratOpt.get());

        return true;
    }
}
