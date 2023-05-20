package hr.fer.ika.ikasus.tests.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import hr.fer.ika.ikasus.DTO.incoming.CreateRentalDetail;
import hr.fer.ika.ikasus.DTO.incoming.DeleteRequest;
import hr.fer.ika.ikasus.DTO.outgoing.CommonResponse;
import hr.fer.ika.ikasus.DTO.outgoing.RentalDetail;
import hr.fer.ika.ikasus.service.RentalService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

/**
 * @author Luka Ćurić
 */
@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RentalControllerTest {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static final String RENTAL_REQUEST_ROOT_PATH = "/api/authenticated/rentals";
    private static final Instant NOW = Instant.now();

    private List<RentalDetail> rentalDetails = new ArrayList<>();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RentalService rentalService;

    private static List<Instant> pickInstant(Instant now, int i) {
        Instant from;
        Instant to;
        List<Instant> instants;

        switch (i % 4) {
            case 0 -> {
                from = now.minus(80, ChronoUnit.DAYS);
                to = now.minus(50, ChronoUnit.DAYS);
                instants = List.of(from, to);
            }

            case 1 -> {
                from = now.minus(50, ChronoUnit.DAYS);
                to = now.minus(40, ChronoUnit.DAYS);
                instants = List.of(from, to);
            }

            case 2 -> {
                from = now.minus(30, ChronoUnit.DAYS);
                to = now.minus(20, ChronoUnit.DAYS);
                instants = List.of(from, to);
            }

            default -> {
                from = now.plus(20, ChronoUnit.DAYS);
                to = now.plus(40, ChronoUnit.DAYS);
                instants = List.of(from, to);
            }
        }

        return instants;
    }

    private void setupMocks() {
        doReturn(rentalDetails).when(this.rentalService).getAllRentalDetails();

        doAnswer((invocationOnMock -> {
            Integer id = invocationOnMock.getArgument(0);

            for (RentalDetail rd : rentalDetails) {
                if (Objects.equals(rd.getId(), id)) {
                    return rd;
                }
            }

            return null;
        })).when(this.rentalService).getRentalDetail(anyInt());

        doAnswer(invocationOnMock -> {
            CreateRentalDetail crd = invocationOnMock.getArgument(0);

            if (crd.getContractId() == null || crd.getVehicleId() == null) {
                return null;
            }

            return 3;
        }).when(this.rentalService).createRental(any());

        doAnswer(invocationOnMock -> {
            Integer id = invocationOnMock.getArgument(0);
            CreateRentalDetail crd = invocationOnMock.getArgument(1);

            return id <= 2 && crd.getContractId() != null && crd.getVehicleId() != null;
        }).when(this.rentalService).updateRental(anyInt(), any());

        doReturn(true).when(this.rentalService).deleteRental(anyInt());
    }

    @BeforeEach
    public void setupData() {
        setupMocks();

        RentalDetail rd1 = new RentalDetail();
        rd1.setId(1);
        rd1.setContractId(4);
        rd1.setVehicleId(6);
        rd1.setKmDriven(125000);
        rd1.setActive(false);

        List<Instant> instants = pickInstant(NOW, 3);
        rd1.setTimeFrom(Date.from(instants.get(0)));
        rd1.setTimeTo(Date.from(instants.get(1)));

        this.rentalDetails.add(rd1);

        RentalDetail rd2 = new RentalDetail();
        rd2.setId(2);
        rd2.setContractId(5);
        rd2.setVehicleId(7);
        rd2.setKmDriven(12000);
        rd2.setActive(true);

        instants = pickInstant(NOW, 2);
        rd2.setTimeFrom(Date.from(instants.get(0)));
        rd2.setTimeTo(Date.from(instants.get(1)));

        this.rentalDetails.add(rd2);
    }

    @AfterEach
    public void teardownData() {
        this.rentalDetails = new ArrayList<>();
    }

    private boolean resultDetailEq(RentalDetail rd1, RentalDetail rd2) {
        return Objects.equals(rd1.getId(), rd2.getId()) && Objects.equals(rd1.getActive(), rd2.getActive()) &&
                Objects.equals(rd1.getContractId(), rd2.getContractId()) &&
                Objects.equals(rd1.getTimeFrom(), rd2.getTimeFrom()) &&
                Objects.equals(rd1.getTimeTo(), rd2.getTimeTo()) &&
                Objects.equals(rd1.getVehicleId(), rd2.getVehicleId()) &&
                Objects.equals(rd1.getKmDriven(), rd2.getKmDriven());
    }

    @Test
    public void find_all_succeeds() throws Exception {
        MvcResult res = this.mockMvc.perform(get(RENTAL_REQUEST_ROOT_PATH)).andReturn();
        MockHttpServletResponse response = res.getResponse();

        int status = response.getStatus();
        assertThat(status).isEqualTo(200);

        String retVal = response.getContentAsString();
        RentalDetail[] responseRentals = objectMapper.readValue(retVal, RentalDetail[].class);

        assertThat(Arrays.stream(responseRentals).allMatch(
                rd -> resultDetailEq(rd, rentalDetails.get(0)) || resultDetailEq(rd, rentalDetails.get(1)))
        ).isTrue();
    }

    @Test
    public void find_by_existing_id_succeeds() throws Exception {
        MvcResult res = this.mockMvc.perform(get(RENTAL_REQUEST_ROOT_PATH + "/1")).andReturn();
        MockHttpServletResponse response = res.getResponse();

        int status = response.getStatus();
        assertThat(status).isEqualTo(200);

        String retVal = response.getContentAsString();
        RentalDetail responseRental = objectMapper.readValue(retVal, RentalDetail.class);

        Optional<RentalDetail> rdStored = rentalDetails.stream()
                .filter(rd -> Objects.equals(rd.getId(), 1)).findFirst();

        assertThat(rdStored.isPresent()).isTrue();

        assertThat(resultDetailEq(responseRental, rdStored.get())).isTrue();
    }

    @Test
    public void find_by_non_existing_id_fails() throws Exception {
        MvcResult res = this.mockMvc.perform(get(RENTAL_REQUEST_ROOT_PATH + "/15")).andReturn();
        MockHttpServletResponse response = res.getResponse();

        int status = response.getStatus();
        assertThat(status).isEqualTo(400);
    }

    @Test
    public void create_new_succeeds() throws Exception {
        CreateRentalDetail crd = new CreateRentalDetail();
        crd.setContractId(7);
        crd.setVehicleId(8);
        crd.setKmDriven(5000);
        crd.setActive(false);

        List<Instant> instants = pickInstant(NOW, 1);
        crd.setTimeFrom(Date.from(instants.get(0)));
        crd.setTimeTo(Date.from(instants.get(1)));

        MvcResult res = this.mockMvc.perform(
                post(RENTAL_REQUEST_ROOT_PATH)
                        .header("Content-Type", "application/json")
                        .content(objectMapper.writeValueAsString(crd))
        ).andReturn();

        MockHttpServletResponse response = res.getResponse();

        assertThat(response.getStatus()).isEqualTo(201);
        assertThat(response.getHeader("Location")).endsWith("/3");
    }

    @Test
    public void create_new_fails() throws Exception {
        CreateRentalDetail crd = new CreateRentalDetail();

        MvcResult res = this.mockMvc.perform(
                post(RENTAL_REQUEST_ROOT_PATH)
                        .header("Content-Type", "application/json")
                        .content(objectMapper.writeValueAsString(crd))
        ).andReturn();

        MockHttpServletResponse response = res.getResponse();

        assertThat(response.getStatus()).isEqualTo(400);

        assertThat(objectMapper.readValue(response.getContentAsString(), CommonResponse.class))
                .isInstanceOf(CommonResponse.class);
    }

    @Test
    public void update_succeeds() throws Exception {
        CreateRentalDetail crd = new CreateRentalDetail();
        crd.setContractId(7);
        crd.setVehicleId(8);

        MvcResult res = this.mockMvc.perform(
                patch(RENTAL_REQUEST_ROOT_PATH + "/2")
                        .header("Content-Type", "application/json")
                        .content(objectMapper.writeValueAsString(crd))
        ).andReturn();

        MockHttpServletResponse response = res.getResponse();

        assertThat(response.getStatus()).isEqualTo(204);
    }

    @Test
    public void update_fails() throws Exception {
        CreateRentalDetail crd = new CreateRentalDetail();

        MvcResult res = this.mockMvc.perform(
                patch(RENTAL_REQUEST_ROOT_PATH + "/2")
                        .header("Content-Type", "application/json")
                        .content(objectMapper.writeValueAsString(crd))
        ).andReturn();

        MockHttpServletResponse response = res.getResponse();

        assertThat(response.getStatus()).isEqualTo(400);

        assertThat(objectMapper.readValue(response.getContentAsString(), CommonResponse.class))
                .isInstanceOf(CommonResponse.class);
    }

    @Test
    public void delete_succeeds() throws Exception {
        DeleteRequest<Integer> dr = new DeleteRequest<>();
        dr.setId(2);

        MvcResult res = this.mockMvc.perform(
                delete(RENTAL_REQUEST_ROOT_PATH)
                        .header("Content-Type", "application/json")
                        .content(objectMapper.writeValueAsString(dr))
        ).andReturn();

        MockHttpServletResponse response = res.getResponse();

        assertThat(response.getStatus()).isEqualTo(204);
    }
}
