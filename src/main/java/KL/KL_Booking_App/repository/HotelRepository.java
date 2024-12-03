package KL.KL_Booking_App.repository;

import KL.KL_Booking_App.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
}
