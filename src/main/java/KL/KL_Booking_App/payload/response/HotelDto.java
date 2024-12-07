package KL.KL_Booking_App.payload.response;

import KL.KL_Booking_App.entity.Location;
import KL.KL_Booking_App.entity.Room;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import lombok.Builder;

import java.util.List;
@Builder
public class HotelDto {

    private Long hotelId;

    @Max(value = 10, message = "Phone number max is 10 number")
    private String phoneNumber;

    @Email
    private String email;

    private String description;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Room> rooms;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Location location;

    public HotelDto() {
    }

    public HotelDto(Long hotelId, String phoneNumber, String email, String description, List<Room> rooms, Location location) {
        this.hotelId = hotelId;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.description = description;
        this.rooms = rooms;
        this.location = location;
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
