package KL.KL_Booking_App.service;

import KL.KL_Booking_App.payload.response.ReviewDto;

public interface IReviewService {
    ReviewDto update(ReviewDto reviewDto);
    ReviewDto save(Long RoomId ,ReviewDto reviewDto);
    void delete(Long reviewId);
}
