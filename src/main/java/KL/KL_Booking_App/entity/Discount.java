package KL.KL_Booking_App.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "Discount")
@Builder
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long discountId;

    private String code;

    @Column(name = "discountAmount")
    private double discountAmount;

    @Column(name = "expirationDate")
    private LocalDate expirationDate;

    @CreationTimestamp
    @Column(name = "createdAt",updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updatedAt",insertable = false)
    private Timestamp updatedAt;

    @OneToMany(mappedBy = "discount", cascade = CascadeType.ALL)
//    @JsonBackReference
    private List<Reservation> reservations;

    public Discount() {
    }

    public Discount(Long discountId, String code, double discountAmount, LocalDate expirationDate, Timestamp createdAt, Timestamp updatedAt,  List<Reservation> reservations) {
        this.discountId = discountId;
        this.code = code;
        this.discountAmount = discountAmount;
        this.expirationDate = expirationDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.reservations = reservations;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public Long getDiscountId() {
        return discountId;
    }

    public void setDiscountId(Long discountId) {
        this.discountId = discountId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
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
}
