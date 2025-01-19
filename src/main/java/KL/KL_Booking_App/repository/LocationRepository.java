package KL.KL_Booking_App.repository;

import KL.KL_Booking_App.entity.Location;
import KL.KL_Booking_App.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Long> {
    Location findByHotelHotelId(Long hotelId);
}
