package KL.KL_Booking_App.payload.response;

import KL.KL_Booking_App.entity.Reservation;
import KL.KL_Booking_App.entity.paymentType.PaymentMethod;
import KL.KL_Booking_App.entity.paymentType.PaymentType;
import lombok.Builder;
import java.time.LocalDateTime;

@Builder
public class PaymentDto {
    private Long paymentId;

    private String amount;

    private  String orderInfo;

    private String transactionId;

    private String paymentUrl;

    private LocalDateTime paymentDate;

    private PaymentType paymentStatus;

    private PaymentMethod paymentMethod;

    private Reservation reservation;

    public PaymentDto() {
    }

    public PaymentDto(Long paymentId, String amount, String orderInfo, String transactionId, String paymentUrl, LocalDateTime paymentDate, PaymentType paymentStatus, PaymentMethod paymentMethod, Reservation reservation) {
        this.paymentId = paymentId;
        this.amount = amount;
        this.orderInfo = orderInfo;
        this.transactionId = transactionId;
        this.paymentUrl = paymentUrl;
        this.paymentDate = paymentDate;
        this.paymentStatus = paymentStatus;
        this.paymentMethod = paymentMethod;
        this.reservation = reservation;
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

    public String getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(String orderInfo) {
        this.orderInfo = orderInfo;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getPaymentUrl() {
        return paymentUrl;
    }

    public void setPaymentUrl(String paymentUrl) {
        this.paymentUrl = paymentUrl;
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

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }
}
