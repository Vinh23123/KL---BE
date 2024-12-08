package KL.KL_Booking_App.repository;

import KL.KL_Booking_App.entity.Discount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountRepository extends JpaRepository<Discount, Long> {
}
