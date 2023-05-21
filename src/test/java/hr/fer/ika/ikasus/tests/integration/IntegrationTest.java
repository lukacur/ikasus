package hr.fer.ika.ikasus.tests.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import hr.fer.ika.ikasus.DTO.incoming.AuthRequest;
import hr.fer.ika.ikasus.DTO.outgoing.AuthResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * @author Luka Ćurić
 */
@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@SpringBootTest
public class IntegrationTest {
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

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
        this.objectMapper = new ObjectMapper();
    }

    private MvcResult performLogin(AuthRequest req, AuthType type) throws Exception {
        String endpooint = "/auth" + type.getEndpoint();

        return this.mockMvc.perform(
                post(endpooint)
                        .header("Content-Type", "application/json")
                        .content(this.objectMapper.writeValueAsString(req))
        ).andDo(print()).andReturn();
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
}
