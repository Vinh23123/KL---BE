package KL.KL_Booking_App.service.impl;

import KL.KL_Booking_App.entity.Review;
import KL.KL_Booking_App.entity.Room;
import KL.KL_Booking_App.exeption.ResourceNotFoundException;
import KL.KL_Booking_App.payload.response.ReviewDto;
import KL.KL_Booking_App.repository.ReviewRepository;
import KL.KL_Booking_App.service.IReviewService;
import KL.KL_Booking_App.service.IRoomService;
import KL.KL_Booking_App.utils.RoomUtils;
import org.springframework.stereotype.Service;

@Service
public class ReviewServiceImpl implements IReviewService {

    private final RoomUtils roomUtils;

    private final IRoomService roomService;

    private final ReviewRepository reviewRepository;

    public ReviewServiceImpl(RoomUtils roomUtils, IRoomService roomService, ReviewRepository reviewRepository) {
        this.roomUtils = roomUtils;
        this.roomService = roomService;
        this.reviewRepository = reviewRepository;
    }

    @Override
    public ReviewDto update(ReviewDto reviewDto) {
        Review review = findReviewById(reviewDto.getReviewId());
        review.setRating(reviewDto.getRating());
        review.setTitle(reviewDto.getTitle());
        review.setComment(reviewDto.getComment());

        Review savedReview = reviewRepository.save(review);

        return  mapReviewToDto(savedReview);
    }

    @Override
    public ReviewDto save(Long RoomId, ReviewDto reviewDto) {
        Room room = roomUtils.mapToRoomEntity(roomService.getRoomById(RoomId));

        Review review = Review
                .builder()
                .rating(reviewDto.getRating())
                .title(reviewDto.getTitle())
                .comment(reviewDto.getComment())
                .room(room)
                .build();

        Review savedReview = reviewRepository.save(review);

        return mapReviewToDto(savedReview);
    }

    @Override
    public void delete(Long reviewId) {
        Review review = findReviewById(reviewId);
        reviewRepository.delete(review);
    }

    private Review findReviewById(Long reviewId){
        return reviewRepository.findById(reviewId).orElseThrow(() ->  new ResourceNotFoundException("Review", "Id", reviewId));
    }

    private ReviewDto mapReviewToDto(Review review){
        return ReviewDto
                .builder()
                .reviewId(review.getReviewId())
                .rating(review.getRating())
                .title(review.getTitle())
                .comment(review.getComment())
                .room(review.getRoom())
                .build();
    }
}
