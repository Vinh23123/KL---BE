package KL.KL_Booking_App.entity;

import KL.KL_Booking_App.entity.paymentType.PaymentMethod;
import KL.KL_Booking_App.entity.paymentType.PaymentType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "Payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    private String amount;

    private  String orderInfo;

    @Column(name = "transactionId",unique = true)
    private String transactionId;

    @Column(name = "paymentDate")
    private LocalDateTime paymentDate;

    @Column(name = "paymentStatus")
    @Enumerated(EnumType.STRING)
    private PaymentType paymentStatus;

    @Column(name = "paymentMethod")
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @CreationTimestamp
    @Column(name = "createdAt",updatable = false)
    private Timestamp createdAt;

    private String vnpTransactionRef;

    @UpdateTimestamp
    @Column(name = "updatedAt",insertable = false)
    private Timestamp updatedAt;

    @ManyToOne
    @JoinColumn(name = "reservation_id")
    @JsonIgnore
    private Reservation reservation;

    public Payment() {
    }

    public Payment(Long paymentId, String amount, String orderInfo, String transactionId, LocalDateTime paymentDate, PaymentType paymentStatus, PaymentMethod paymentMethod, Timestamp createdAt, String vnpTransactionRef, Timestamp updatedAt, Reservation reservation) {
        this.paymentId = paymentId;
        this.amount = amount;
        this.orderInfo = orderInfo;
        this.transactionId = transactionId;
        this.paymentDate = paymentDate;
        this.paymentStatus = paymentStatus;
        this.paymentMethod = paymentMethod;
        this.createdAt = createdAt;
        this.vnpTransactionRef = vnpTransactionRef;
        this.updatedAt = updatedAt;
        this.reservation = reservation;
    }

    public String getVnpTransactionRef() {
        return vnpTransactionRef;
    }

    public void setVnpTransactionRef(String vnpTransactionRef) {
        this.vnpTransactionRef = vnpTransactionRef;
    }

    public String getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(String orderInfo) {
        this.orderInfo = orderInfo;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public PaymentType getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentType paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
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

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }
}
