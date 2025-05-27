package smartcart.org.response;

import lombok.Data;

@Data
public class ApiResponse<T> {
    private Boolean success;
    private Integer status;
    private T data;
    private String message;


    public ApiResponse(Boolean success, Integer status, T data) {
        this.success = success;
        this.status = status;
        this.data = data;
    }

    public ApiResponse(Boolean success, Integer status, T data, String message) {
        this.success = success;
        this.status = status;
        this.data = data;
        this.message = message;
    }
}
