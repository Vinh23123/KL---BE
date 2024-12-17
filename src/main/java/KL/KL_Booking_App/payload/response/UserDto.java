package KL.KL_Booking_App.payload.response;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
public class UserDto {
    private long userId;

    private String firstName;

    private String lastName;

    @NotBlank(message = "Phone number must not be blank.")
    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be exactly 10 digits.")
    private String phone;

    @Email
    // not null, not an empty string, and does not consist entirely of whitespace.
    @NotBlank(message = "Email must not be blank")
    @Column(unique = true)
    private String mail;

    public UserDto() {
    }

    public UserDto(long userId, String firstName, String lastName, String phone, String mail) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.mail = mail;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
