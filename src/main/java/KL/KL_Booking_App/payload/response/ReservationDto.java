package KL.KL_Booking_App.payload.response;

import KL.KL_Booking_App.entity.Discount;
import KL.KL_Booking_App.entity.Payment;
    import KL.KL_Booking_App.entity.ReservationRoom;
import KL.KL_Booking_App.entity.Room;
import KL.KL_Booking_App.entity.reservationType.ReservationType;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Builder
public class ReservationDto {
    private Long reservationId;

//    private ReservationType reservationType;

    @FutureOrPresent(message = "The check in date must not be in the past.")
    @NotNull(message = "The check in date must be not null")
    private LocalDateTime checkIn;

    @FutureOrPresent(message = "The check out date must not be in the past.")
    @NotNull(message = "The check out date must be not null")
    private LocalDateTime checkOut;

    private double totalAmount;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Discount discount;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ReservationRoom> reservationRoom;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Payment payment;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<RoomDto> rooms;

    private UserDto userDto;

    public ReservationDto(Long reservationId, LocalDateTime checkIn, LocalDateTime checkOut, double totalAmount, Discount discount, List<ReservationRoom> reservationRoom, Payment payment, List<RoomDto> rooms, UserDto userDto) {
        this.reservationId = reservationId;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.totalAmount = totalAmount;
        this.discount = discount;
        this.reservationRoom = reservationRoom;
        this.payment = payment;
        this.rooms = rooms;
        this.userDto = userDto;
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }

    public List<RoomDto> getRooms() {
        return rooms;
    }

    public void setRooms(List<RoomDto> rooms) {
        this.rooms = rooms;
    }

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public LocalDateTime getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDateTime checkIn) {
        this.checkIn = checkIn;
    }

    public LocalDateTime getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDateTime checkOut) {
        this.checkOut = checkOut;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    public List<ReservationRoom> getReservationRoom() {
        return reservationRoom;
    }

    public void setReservationRoom(List<ReservationRoom> reservationRoom) {
        this.reservationRoom = reservationRoom;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }
}
