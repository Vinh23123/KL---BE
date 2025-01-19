package KL.KL_Booking_App.payload.response;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public class DiscountDto {

    private Long discountId;

    private String code;

    private double discountAmount;

    private LocalDate expirationDate;

    public DiscountDto() {
    }

    public DiscountDto(Long discountId, String code, double discountAmount, LocalDate expirationDate) {
        this.discountId = discountId;
        this.code = code;
        this.discountAmount = discountAmount;
        this.expirationDate = expirationDate;
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
}
