package KL.KL_Booking_App.service;

import KL.KL_Booking_App.payload.request.RequestRating;
import KL.KL_Booking_App.payload.request.RequestReview;
import KL.KL_Booking_App.payload.response.ReviewDto;

public interface IReviewService {
    ReviewDto update(ReviewDto reviewDto);

    ReviewDto updateRating(RequestRating reviewDto);

    ReviewDto save(Long RoomId , RequestReview reviewDto);
    void delete(Long reviewId);
}
