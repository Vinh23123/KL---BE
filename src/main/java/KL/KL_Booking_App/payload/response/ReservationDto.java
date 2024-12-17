package KL.KL_Booking_App.payload.response;

import KL.KL_Booking_App.entity.Discount;
import KL.KL_Booking_App.entity.Payment;
    import KL.KL_Booking_App.entity.ReservationRoom;
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

    private ReservationType reservationType;

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
    private List<Payment> payment;

    public ReservationDto(Long reservationId, ReservationType reservationType, LocalDateTime checkIn, LocalDateTime checkOut, double totalAmount, Discount discount, List<ReservationRoom> reservationRoom, List<Payment> payment) {
        this.reservationId = reservationId;
        this.reservationType = reservationType;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.totalAmount = totalAmount;
        this.discount = discount;
        this.reservationRoom = reservationRoom;
        this.payment = payment;
    }

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public ReservationType getReservationType() {
        return reservationType;
    }

    public void setReservationType(ReservationType reservationType) {
        this.reservationType = reservationType;
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

    public List<Payment> getPayment() {
        return payment;
    }

    public void setPayment(List<Payment> payment) {
        this.payment = payment;
    }
}
