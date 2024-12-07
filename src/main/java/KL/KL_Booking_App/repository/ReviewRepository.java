package KL.KL_Booking_App.repository;

import KL.KL_Booking_App.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
