package KL.KL_Booking_App.controller;

import KL.KL_Booking_App.constants.Global;
import KL.KL_Booking_App.constants.api.ReviewApi;
import KL.KL_Booking_App.constants.api.RoomApi;
import KL.KL_Booking_App.payload.request.RequestRating;
import KL.KL_Booking_App.payload.request.RequestReview;
import KL.KL_Booking_App.payload.response.Response;
import KL.KL_Booking_App.payload.response.ReviewDto;
import KL.KL_Booking_App.service.IReviewService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("api/v1")
@CrossOrigin(origins = "http://localhost:5173")
public class ReviewController {

    private static final Logger log = LoggerFactory.getLogger(ReviewController.class);
    private final IReviewService reviewService;

    public ReviewController(IReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping(RoomApi.ROOM_BY_ID + ReviewApi.REVIEWS )
    public ResponseEntity<Response> createAReview(@PathVariable(value = "roomId") Long roomId, @Valid @RequestBody RequestReview reviewDto){

        try {
            ReviewDto savedReviewDto = reviewService.save(roomId, reviewDto);
            return new ResponseEntity<>( Response.builder()
                    .status(Global.STATUS_SUCCESS)
                    .data(savedReviewDto)
                    .message(Global.MESSAGE_CREATED_SUCCESSFULLY)
                    .time(new SimpleDateFormat("dd-MM-yyyy").format(new Date()))
                    .build(), HttpStatus.CREATED);
        }catch (Exception e){
            log.info(e.getMessage());
            return new ResponseEntity<>( Response.builder()
                    .status(Global.STATUS_FAILED)
                    .message(e.getMessage())
                    .time(new SimpleDateFormat("dd-MM-yyyy").format(new Date()))
                    .build(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(ReviewApi.REVIEW_BY_ID )
    public ResponseEntity<Response> deleteAReview(@PathVariable(value = "reviewId") Long reviewId){

        try {
            reviewService.delete(reviewId);
            return new ResponseEntity<>( Response.builder()
                    .status(Global.STATUS_SUCCESS)
                    .message(Global.MESSAGE_DELETED_SUCCESSFULLY)
                    .time(new SimpleDateFormat("dd-MM-yyyy").format(new Date()))
                    .build(), HttpStatus.OK);
        }catch (Exception e){
            log.info(e.getMessage());
            return new ResponseEntity<>( Response.builder()
                    .status(Global.STATUS_FAILED)
                    .message(e.getMessage())
                    .time(new SimpleDateFormat("dd-MM-yyyy").format(new Date()))
                    .build(), HttpStatus.NOT_FOUND);
        }
    }


    @PutMapping(ReviewApi.REVIEWS)
    public ResponseEntity<Response> updateAReview(@Valid @RequestBody ReviewDto reviewDto){
        try {
             ReviewDto updatedReviewDto = reviewService.update(reviewDto);
            return new ResponseEntity<>( Response.builder()
                    .status(Global.STATUS_SUCCESS)
                    .data(updatedReviewDto)
                    .message(Global.MESSAGE_UPDATED_SUCCESSFULLY)
                    .time(new SimpleDateFormat("dd-MM-yyyy").format(new Date()))
                    .build(), HttpStatus.OK);
        }catch (Exception e){
            log.info(e.getMessage());
            return new ResponseEntity<>( Response.builder()
                    .status(Global.STATUS_FAILED)
                    .message(e.getMessage())
                    .time(new SimpleDateFormat("dd-MM-yyyy").format(new Date()))
                    .build(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(ReviewApi.REVIEWS + "/rating")
    public ResponseEntity<Response> updateAReviewRating(@Valid @RequestBody RequestRating reviewDto){
        try {
            ReviewDto updatedReviewDto = reviewService.updateRating(reviewDto);
            return new ResponseEntity<>( Response.builder()
                    .status(Global.STATUS_SUCCESS)
                    .data(updatedReviewDto)
                    .message(Global.MESSAGE_UPDATED_SUCCESSFULLY)
                    .time(new SimpleDateFormat("dd-MM-yyyy").format(new Date()))
                    .build(), HttpStatus.OK);
        }catch (Exception e){
            log.info(e.getMessage());
            return new ResponseEntity<>( Response.builder()
                    .status(Global.STATUS_FAILED)
                    .message(e.getMessage())
                    .time(new SimpleDateFormat("dd-MM-yyyy").format(new Date()))
                    .build(), HttpStatus.NOT_FOUND);
        }
    }
}
