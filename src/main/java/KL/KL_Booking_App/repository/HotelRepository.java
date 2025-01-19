package KL.KL_Booking_App.repository;

import KL.KL_Booking_App.entity.Hotel;
import KL.KL_Booking_App.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
    List<Hotel> findByUserUserId(Long userId);
}
