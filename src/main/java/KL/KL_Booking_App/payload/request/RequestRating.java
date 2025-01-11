package KL.KL_Booking_App.payload.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public class RequestRating {
    private Long reviewId;

    @Min(value = 1, message = "The min rating can not be less than 1")
    @Max(value = 5, message =  "The max rating can not be greater than 5")
    private int rating;

    public RequestRating(Long reviewId, int rating) {
        this.reviewId = reviewId;
        this.rating = rating;
    }

    public RequestRating() {
    }

    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
