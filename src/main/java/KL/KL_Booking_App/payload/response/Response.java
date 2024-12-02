package KL.KL_Booking_App.payload.response;

import lombok.Builder;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Response {
    private String status;
    private Object data;
    private String message;
    private String time;

    public Response() {
    }

    public Response(String status, Object data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
        this.time = getCurrentTime();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
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

    private String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return sdf.format(new Date());
    }
}
