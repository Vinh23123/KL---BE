package KL.KL_Booking_App.service.impl;

import KL.KL_Booking_App.entity.Review;
import KL.KL_Booking_App.entity.Room;
import KL.KL_Booking_App.entity.User;
import KL.KL_Booking_App.exeption.ResourceNotFoundException;
import KL.KL_Booking_App.payload.request.RequestRating;
import KL.KL_Booking_App.payload.request.RequestReview;
import KL.KL_Booking_App.payload.response.ReviewDto;
import KL.KL_Booking_App.repository.ReviewRepository;
import KL.KL_Booking_App.repository.UserRepository;
import KL.KL_Booking_App.service.IReviewService;
import KL.KL_Booking_App.service.IRoomService;
import KL.KL_Booking_App.service.sec.UserDetailsImpl;
import KL.KL_Booking_App.utils.RoomUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ReviewServiceImpl implements IReviewService {

    private final RoomUtils roomUtils;

    private final IRoomService roomService;

    private final ReviewRepository reviewRepository;

    private final UserRepository userRepository;

    public ReviewServiceImpl(RoomUtils roomUtils, IRoomService roomService, ReviewRepository reviewRepository, UserRepository userRepository) {
        this.roomUtils = roomUtils;
        this.roomService = roomService;
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ReviewDto update(ReviewDto reviewDto) {
        Review review = findReviewById(reviewDto.getReviewId());

        review.setTitle(reviewDto.getTitle());
        review.setComment(reviewDto.getComment());

        Review savedReview = reviewRepository.save(review);

        return  mapReviewToDto(savedReview);
    }

    @Override
    public ReviewDto updateRating(RequestRating reviewDto) {
        Review review = findReviewById(reviewDto.getReviewId());
        review.setRating(reviewDto.getRating());
        Review savedReview = reviewRepository.save(review);

        return  mapReviewToDto(savedReview);
    }

    @Override
    public ReviewDto save(Long RoomId, RequestReview reviewDto) {
        Room room = roomUtils.mapToRoomEntity(roomService.getRoomById(RoomId));

        // will replace soon
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = userDetails.getId();

        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

        Review review = Review
                .builder()
                .title(reviewDto.getTitle())
                .comment(reviewDto.getComment())
                .room(room)
                .user(user)
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
//                .user(review.getUser())
                .build();
    }
}
