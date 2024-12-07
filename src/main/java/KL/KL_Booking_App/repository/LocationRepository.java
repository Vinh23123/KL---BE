package KL.KL_Booking_App.repository;

import KL.KL_Booking_App.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
}
