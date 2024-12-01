package KL.KL_Booking_App.repository;

import KL.KL_Booking_App.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository <Room, Long> {
}
