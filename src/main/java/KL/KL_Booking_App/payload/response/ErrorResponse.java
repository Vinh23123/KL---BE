package KL.KL_Booking_App.payload.response;

public class ErrorResponse {
    private String status;
    private Object message;
    private String time;

    public ErrorResponse(String status, Object message, String time) {
        this.status = status;
        this.message = message;
        this.time = time;
    }

    // Getters and Setters
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
