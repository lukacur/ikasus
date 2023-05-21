package hr.fer.ika.ikasus.tests.services;

import hr.fer.ika.ikasus.DAO.Najam;
import hr.fer.ika.ikasus.DAO.Ugovor;
import hr.fer.ika.ikasus.DAO.Vozilo;
import hr.fer.ika.ikasus.DTO.incoming.CreateRentalDetail;
import hr.fer.ika.ikasus.DTO.outgoing.RentalDetail;
import hr.fer.ika.ikasus.repository.NajamRepository;
import hr.fer.ika.ikasus.repository.UgovorRepository;
import hr.fer.ika.ikasus.repository.VoziloRepository;
import hr.fer.ika.ikasus.service.RentalService;
import hr.fer.ika.ikasus.tests.util.InstantUtil;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

import static hr.fer.ika.ikasus.tests.util.InstantUtil.InstantRange;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * @author Luka Ćurić
 */
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RentalServiceTest {
    private static final Instant NOW = Instant.now();

    private Map<Integer, Najam> rentals = new HashMap<>();
    private Map<Integer, Ugovor> contracts = new HashMap<>();
    private Map<Integer, Vozilo> vehicles = new HashMap<>();

    @Autowired
    private RentalService rentalService;

    @MockBean
    private NajamRepository najamRepository;

    @MockBean
    private UgovorRepository ugovorRepository;

    @MockBean
    private VoziloRepository voziloRepository;

    @BeforeEach
    public void setupData() {
        for (int i = 1; i < 4; i++) {
            Vozilo v = new Vozilo();
            v.setId(i);

            Ugovor u = new Ugovor();
            u.setId(i);

            this.vehicles.put(i, v);
            this.contracts.put(i, u);
        }

        for (int i = 1; i < 4; i++) {
            Najam n = new Najam();
            n.setId(i);
            n.setIdugovor(this.contracts.get(1));
            n.setIdvozilo(this.vehicles.get(2));
            n.setAktivan(true);

            InstantRange picked = InstantUtil.pickInstant(NOW, i - 1);
            Instant timeFrom = picked.from();
            Instant timeTo = picked.to();
            n.setVrijemeod(timeFrom);
            n.setVrijemedo(timeTo);

            this.rentals.put(i, n);
        }

        doAnswer(invocationOnMock -> {
            Integer id = invocationOnMock.getArgument(0);

            Vozilo v = this.vehicles.get(id);

            if (v != null) {
                return Optional.of(v);
            }

            return Optional.empty();
        }).when(voziloRepository).findById(anyInt());

        when(voziloRepository.findAll()).thenReturn(new LinkedList<>(this.vehicles.values()));

        doAnswer(invocationOnMock -> {
            Integer id = invocationOnMock.getArgument(0);

            Ugovor u = this.contracts.get(id);

            if (u != null) {
                return Optional.of(u);
            }

            return Optional.empty();
        }).when(ugovorRepository).findById(anyInt());

        when(ugovorRepository.findAll()).thenReturn(new LinkedList<>(this.contracts.values()));

        doAnswer(invocationOnMock -> {
            Integer id = invocationOnMock.getArgument(0);

            Najam n = this.rentals.get(id);

            if (n != null) {
                return Optional.of(n);
            }

            return Optional.empty();
        }).when(najamRepository).findById(anyInt());

        when(najamRepository.findAll()).thenReturn(new LinkedList<>(this.rentals.values()));

        doAnswer(invocationOnMock -> {
            Vozilo v = invocationOnMock.getArgument(0);

            return this.rentals.values().stream()
                    .filter(r -> Objects.equals(r.getIdvozilo().getId(), v.getId()))
                    .collect(Collectors.toList());
        }).when(najamRepository).findByIdvozilo(any());

        doAnswer(invocationOnMock -> {
            Najam n = invocationOnMock.getArgument(0);

            if (n.getId() == null) {
                n.setId(4);
            }

            return n;
        }).when(najamRepository).save(any());

        doAnswer(invocationOnMock -> {
            Najam n = invocationOnMock.getArgument(0);

            this.rentals.remove(n.getId());

            return Void.TYPE;
        }).when(najamRepository).delete(any());
    }

    @AfterEach
    public void teardownData() {
        this.rentals = new HashMap<>();
        this.contracts = new HashMap<>();
        this.vehicles = new HashMap<>();
    }

    private static boolean isMappedFrom(Najam r, RentalDetail rd) {
        return Objects.equals(r.getId(), rd.getId()) && Objects.equals(Date.from(r.getVrijemeod()), rd.getTimeFrom()) &&
                Objects.equals(Date.from(r.getVrijemedo()), rd.getTimeTo()) &&
                Objects.equals(r.getPrijedeno(), rd.getKmDriven()) && Objects.equals(r.getAktivan(), rd.getActive()) &&
                Objects.equals(r.getIdvozilo().getId(), rd.getVehicleId()) &&
                Objects.equals(r.getIdugovor().getId(), rd.getContractId());
    }

    @Test
    public void find_all_returns_list_of_rental_details() {
        List<RentalDetail> rentalDetails = this.rentalService.getAllRentalDetails();

        for (RentalDetail rd : rentalDetails) {
            Najam r = this.rentals.get(rd.getId());

            assertThat(isMappedFrom(r, rd)).isTrue();
        }
    }

    @Test
    public void find_by_existing_id_returns_rental_detail() {
        RentalDetail rd = this.rentalService.getRentalDetail(1);
        Najam r = this.rentals.get(1);

        assertThat(isMappedFrom(r, rd)).isTrue();
    }

    @Test
    public void find_by_non_existing_id_returns_null() {
        RentalDetail rd = this.rentalService.getRentalDetail(15);

        assertThat(rd).isNull();
    }

    @Test
    public void create_new_returns_created_id() {
        CreateRentalDetail crd = new CreateRentalDetail();
        crd.setContractId(1);
        crd.setVehicleId(2);
        crd.setKmDriven(5000);
        crd.setActive(false);

        for (InstantRange range : InstantUtil.nonOverlappingInstants(NOW)) {
            crd.setTimeFrom(Date.from(range.from()));
            crd.setTimeTo(Date.from(range.to()));
            Integer createdId = this.rentalService.createRental(crd);
            assertThat(createdId)
                    .overridingErrorMessage(() -> String.format("Failed on range %s", range.name()))
                    .isEqualTo(4);
        }
    }

    @Test
    public void update_to_overlapping_date_returns_false() {
        CreateRentalDetail crd = new CreateRentalDetail();
        crd.setContractId(1);
        crd.setVehicleId(2);
        crd.setKmDriven(5000);
        crd.setActive(false);

        for (InstantRange range : InstantUtil.overlappingInstants(NOW)) {
            crd.setTimeFrom(Date.from(range.from()));
            crd.setTimeTo(Date.from(range.to()));
            assertThat(this.rentalService.updateRental(3, crd))
                    .overridingErrorMessage(() -> String.format("Failed on range %s", range.name()))
                    .isFalse();
        }
    }

    @Test
    public void update_self_to_overlapping_date_with_only_self_returns_true() {
        CreateRentalDetail crd = new CreateRentalDetail();
        crd.setContractId(1);
        crd.setVehicleId(2);
        crd.setKmDriven(5000);
        crd.setActive(false);

        for (InstantRange range : InstantUtil.overlappingInstants(NOW)) {
            crd.setTimeFrom(Date.from(range.from()));
            crd.setTimeTo(Date.from(range.to()));
            assertThat(this.rentalService.updateRental(1, crd))
                    .overridingErrorMessage(() -> String.format("Failed on range %s", range.name()))
                    .isTrue();
        }
    }

    @Test
    public void create_rental_with_overlapping_date_returns_null() {
        CreateRentalDetail crd = new CreateRentalDetail();
        crd.setContractId(1);
        crd.setVehicleId(2);
        crd.setKmDriven(5000);
        crd.setActive(false);

        for (InstantRange range : InstantUtil.overlappingInstants(NOW)) {
            crd.setTimeFrom(Date.from(range.from()));
            crd.setTimeTo(Date.from(range.to()));
            assertThat(this.rentalService.createRental(crd))
                    .overridingErrorMessage(() -> String.format("Failed on range %s", range.name()))
                    .isNull();
        }
    }

    @Test
    public void delete_returns_true() {
        boolean deleted = this.rentalService.deleteRental(1);

        assertThat(deleted).isTrue();
    }

    @Test
    public void consecutive_delete_returns_false() {
        boolean deleted = this.rentalService.deleteRental(1);

        assertThat(deleted).isTrue();

        boolean consecDeleted = this.rentalService.deleteRental(1);
        assertThat(consecDeleted).isFalse();
    }
}
