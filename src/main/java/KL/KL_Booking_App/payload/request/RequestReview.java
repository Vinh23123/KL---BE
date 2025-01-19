package KL.KL_Booking_App.payload.request;


import jakarta.validation.constraints.Size;

public class RequestReview {
    private Long reviewId;

    private String title;

    @Size(max = 1200, message = "The Comment can not be larger than 1200 characters")
    private String comment;

    public RequestReview(Long reviewId, String title, String comment) {
        this.reviewId = reviewId;
        this.title = title;
        this.comment = comment;
    }

    public RequestReview() {
    }

    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
