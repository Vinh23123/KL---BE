package KL.KL_Booking_App.entity;

import KL.KL_Booking_App.entity.reservationType.ReservationType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToOne;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Reservation {
    private int reservationId;

    private ReservationType reservationType;
    private LocalDateTime date;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private double totalAmount;
    @CreationTimestamp
    @Column(name = "createdAt",updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updatedAt",insertable = false)
    private Timestamp updatedAt;

    @OneToOne
    private Discount discount;
}
