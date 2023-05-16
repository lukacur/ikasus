package hr.fer.ika.ikasus.DTO.outgoing;

/**
 * @author Luka Ćurić
 */
public class AuthResponse {
    private String token;

    public AuthResponse() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
