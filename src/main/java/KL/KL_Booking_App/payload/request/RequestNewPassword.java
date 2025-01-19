package KL.KL_Booking_App.payload.request;

import jakarta.validation.constraints.NotBlank;

public class RequestNewPassword {

    @NotBlank
    private String userName;

    @NotBlank
    private String password;

    public RequestNewPassword(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public RequestNewPassword() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
