package hr.fer.ika.ikasus.DTO.outgoing;

/**
 * @author Luka Ćurić
 */
public class CommonResponse {
    private Boolean isError;
    private Object error;
    private Object data;

    public CommonResponse() {
    }

    public Boolean getIsError() {
        return isError;
    }

    public void setIsError(Boolean error) {
        isError = error;
    }

    public Object getError() {
        return error;
    }

    public void setError(Object error) {
        this.error = error;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
