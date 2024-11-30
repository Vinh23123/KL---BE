package KL.KL_Booking_App.entity;

import KL.KL_Booking_App.entity.reservationType.ReservationType;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reservationId;

    @Column(name = "reservationType")
    @Enumerated(EnumType.STRING)
    private ReservationType reservationType;

    private LocalDateTime date;

    @Column(name = "checkIn")
    private LocalDateTime checkIn;

    @Column(name = "checkOut")
    private LocalDateTime checkOut;

    @Column(name = "totalAmount")
    private double totalAmount;

    @CreationTimestamp
    @Column(name = "createdAt",updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updatedAt",insertable = false)
    private Timestamp updatedAt;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "discount_id", referencedColumnName = "discountId")
    private Discount discount;

    @OneToMany(mappedBy = "reservation")
    private List<ReservationRoom> reservationRoom;

    @OneToMany(mappedBy = "reservation")
    private List<Payment> payment;

    public Reservation() {
    }

    public Reservation(int reservationId, ReservationType reservationType, LocalDateTime date, LocalDateTime checkIn, LocalDateTime checkOut, Timestamp createdAt, double totalAmount, Timestamp updatedAt, Discount discount, List<ReservationRoom>  reservationRoom ) {
        this.reservationId = reservationId;
        this.reservationType = reservationType;
        this.date = date;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.createdAt = createdAt;
        this.totalAmount = totalAmount;
        this.updatedAt = updatedAt;
        this.discount = discount;
        this.reservationRoom = reservationRoom;
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public ReservationType getReservationType() {
        return reservationType;
    }

    public void setReservationType(ReservationType reservationType) {
        this.reservationType = reservationType;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
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

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    public  List<ReservationRoom>   getReservationRoom() {
        return reservationRoom;
    }

    public void setReservationRoom( List<ReservationRoom>  reservationRoom) {
        this.reservationRoom = reservationRoom;
    }
}
