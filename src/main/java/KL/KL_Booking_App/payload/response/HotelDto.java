package KL.KL_Booking_App.payload.response;

import KL.KL_Booking_App.entity.Location;
import KL.KL_Booking_App.entity.Room;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

import java.util.List;
@Builder
public class HotelDto {

    private Long hotelId;

    private String hotelName;

    @NotNull(message = "Phone number must not be null.")
    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be exactly 10 digits.")
    private String phoneNumber;

    @Email
    @NotNull
    private String email;

    private String description;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Room> rooms;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Location location;

    public HotelDto() {
    }

    public HotelDto(Long hotelId, String hotelName, String phoneNumber, String email, String description, List<Room> rooms, Location location) {
        this.hotelId = hotelId;
        this.hotelName = hotelName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.description = description;
        this.rooms = rooms;
        this.location = location;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Long getHotelId() {
        return hotelId;
    }

    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
