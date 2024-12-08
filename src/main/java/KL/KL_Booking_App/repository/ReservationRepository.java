package KL.KL_Booking_App.repository;

import KL.KL_Booking_App.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
