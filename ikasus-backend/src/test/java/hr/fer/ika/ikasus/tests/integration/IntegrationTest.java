package hr.fer.ika.ikasus.tests.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import hr.fer.ika.ikasus.DTO.incoming.AuthRequest;
import hr.fer.ika.ikasus.DTO.incoming.CreateRentalRequestInfo;
import hr.fer.ika.ikasus.DTO.inout.VehicleRentalInfo;
import hr.fer.ika.ikasus.DTO.outgoing.AuthResponse;
import hr.fer.ika.ikasus.DTO.outgoing.ContractMaster;
import hr.fer.ika.ikasus.DTO.outgoing.RentalRequestMaster;
import hr.fer.ika.ikasus.DTO.outgoing.RentalRequestVehicleDetail;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * @author Luka Ćurić
 */
@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@SpringBootTest
@AutoConfigureMockMvc
public class IntegrationTest {
    private String managerToken;
    private String customerToken;
    private String employeeToken;

    private enum AuthType {
        CUSTOMER("/customer"),
        MANAGER("/manager"),
        EMPLOYEE("/employee");

        private final String endpoint;

        AuthType(String endpoint) {
            this.endpoint = endpoint;
        }

        public String getEndpoint() {
            return endpoint;
        }
    }

    private static class AuthRequests {
        private static final AuthRequest CUSTOMER = new AuthRequest();
        private static final AuthRequest MANAGER = new AuthRequest();
        private static final AuthRequest EMPLOYEE = new AuthRequest();
        private static final AuthRequest INVALID = new AuthRequest();

        static {
            CUSTOMER.setPrincipal("rroset0@hp.com");
            CUSTOMER.setCredentials("roseroset123");

            MANAGER.setPrincipal("bjessep1");
            MANAGER.setCredentials("LYxPUThiW1");

            EMPLOYEE.setPrincipal("dora@ikasus.com.hr");
            EMPLOYEE.setCredentials("doradoric123");

            INVALID.setPrincipal("invalid@invalid.invalid");
            INVALID.setCredentials("invalidcredentialsforinvaliduser");
        }
    }

    @Autowired
    private WebApplicationContext webApplicationContext;

    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    private void setupTokens() throws Exception {
        AuthResponse resp;

        MockHttpServletResponse mgrRes = this.performLogin(AuthRequests.MANAGER, AuthType.MANAGER).getResponse();
        resp = this.objectMapper.readValue(mgrRes.getContentAsString(), AuthResponse.class);
        this.managerToken = resp.getToken();

        MockHttpServletResponse cstRes = this.performLogin(AuthRequests.CUSTOMER, AuthType.CUSTOMER).getResponse();
        resp = this.objectMapper.readValue(cstRes.getContentAsString(), AuthResponse.class);
        this.customerToken = resp.getToken();

        MockHttpServletResponse empRes = this.performLogin(AuthRequests.EMPLOYEE, AuthType.EMPLOYEE).getResponse();
        resp = this.objectMapper.readValue(empRes.getContentAsString(), AuthResponse.class);
        this.employeeToken = resp.getToken();
    }

    @BeforeEach
    public void setup() throws Exception {
        this.objectMapper = new ObjectMapper();

        this.setupTokens();
    }

    private MvcResult performLogin(AuthRequest req, AuthType type) throws Exception {
        String endpooint = "/auth" + type.getEndpoint();

        return this.mockMvc.perform(
                post(endpooint)
                        .header("Content-Type", "application/json")
                        .content(this.objectMapper.writeValueAsString(req))
        ).andReturn();
    }

    @Test
    public void manager_login_returns_token_for_valid_credentials() throws Exception {
        MvcResult result = performLogin(AuthRequests.MANAGER, AuthType.MANAGER);

        MockHttpServletResponse response = result.getResponse();

        assertThat(response.getStatus()).isEqualTo(200);

        String body = response.getContentAsString();

        AuthResponse tokenResponse = objectMapper.readValue(body, AuthResponse.class);

        assertThat(tokenResponse).isInstanceOf(AuthResponse.class);
    }

    @Test
    public void invalid_user_login_returns_401() throws Exception {
        MvcResult result = performLogin(AuthRequests.INVALID, AuthType.CUSTOMER);

        MockHttpServletResponse response = result.getResponse();

        assertThat(response.getStatus()).isEqualTo(401);
    }

    @Test
    public void manager_get_all_contracts_returns_more_than_when_customer_gets_contracts() throws Exception {
        final String CONTRACTS_ROOT = "/api/authenticated/contracts";
        String authHeader = this.managerToken;

        MvcResult mgrRes = this.mockMvc.perform(
                get(CONTRACTS_ROOT).header("Authorization", "Bearer " + authHeader)
        ).andReturn();

        MockHttpServletResponse mgrResp = mgrRes.getResponse();
        assertThat(mgrResp.getStatus()).isEqualTo(200);

        ContractMaster[] mgrConts = this.objectMapper.readValue(mgrResp.getContentAsString(), ContractMaster[].class);

        authHeader = this.customerToken;

        MvcResult custRes = this.mockMvc.perform(
                get(CONTRACTS_ROOT).header("Authorization", "Bearer " + authHeader)
        ).andReturn();

        MockHttpServletResponse custResp = custRes.getResponse();
        assertThat(custResp.getStatus()).isEqualTo(200);

        ContractMaster[] custConts = this.objectMapper.readValue(custResp.getContentAsString(), ContractMaster[].class);

        assertThat(mgrConts.length).isGreaterThan(custConts.length);
    }

    @Test
    public void customer_create_rental_request() throws Exception {
        final String RENTAL_REQUESTS_ROOT = "/api/authenticated/rental-requests";
        final LocalDate NOW = LocalDate.now();

        CreateRentalRequestInfo rentReqInfo = new CreateRentalRequestInfo();
        VehicleRentalInfo vri = new VehicleRentalInfo();
        vri.setVehicleId(2);
        vri.setRentFrom(Date.from(
                NOW.plus(3650, ChronoUnit.DAYS).atStartOfDay(ZoneId.systemDefault()).toInstant())
        );

        vri.setRentTo(Date.from(
                NOW.plus(3680, ChronoUnit.DAYS).atStartOfDay(ZoneId.systemDefault()).toInstant())
        );

        rentReqInfo.setRequestedRentals(List.of(vri));

        String authHeader = this.customerToken;

        MvcResult res = this.mockMvc.perform(
                post(RENTAL_REQUESTS_ROOT)
                        .header("Authorization", "Bearer " + authHeader)
                        .header("Content-Type", "application/json")
                        .content(this.objectMapper.writeValueAsString(rentReqInfo))
        ).andReturn();

        MockHttpServletResponse resp = res.getResponse();
        assertThat(resp.getStatus()).isEqualTo(201);
        assertThat(resp.getHeader("Location")).isNotNull();

        String rReqIdPath = RENTAL_REQUESTS_ROOT + resp.getHeader("Location");

        res = this.mockMvc.perform(
                get(rReqIdPath).header("Authorization", "Bearer " + authHeader)
        ).andReturn();

        resp = res.getResponse();

        assertThat(resp.getStatus()).isEqualTo(200);

        RentalRequestMaster rentReqMaster =
                this.objectMapper.readValue(resp.getContentAsString(), RentalRequestMaster.class);

        assertThat(rentReqMaster.getVehicleDetails().size()).isEqualTo(1);

        RentalRequestVehicleDetail detail = rentReqMaster.getVehicleDetails().get(0);

        assertThat(detail.getVehicleId()).isEqualTo(vri.getVehicleId());
        assertThat(detail.getRentFrom()).isEqualTo(vri.getRentFrom());
        assertThat(detail.getRentTo()).isEqualTo(vri.getRentTo());
    }
}
