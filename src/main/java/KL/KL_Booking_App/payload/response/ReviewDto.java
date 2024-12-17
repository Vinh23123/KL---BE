package KL.KL_Booking_App.payload.response;
import KL.KL_Booking_App.entity.Room;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class ReviewDto {

    private Long reviewId;

    @Min(value = 1, message = "The min rating can not be less than 1")
    @Max(value = 5, message =  "The max rating can not be greater than 5")
    private int rating;

    private String title;

    private String comment;

    private LocalDateTime reviewDate;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Room room;

    public ReviewDto() {
    }

    public ReviewDto(Long reviewId, int rating, String title, String comment, LocalDateTime reviewDate, Room room) {
        this.reviewId = reviewId;
        this.rating = rating;
        this.title = title;
        this.comment = comment;
        this.reviewDate = reviewDate;
        this.room = room;
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

    public LocalDateTime getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(LocalDateTime reviewDate) {
        this.reviewDate = reviewDate;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
