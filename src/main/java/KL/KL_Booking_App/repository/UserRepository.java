package KL.KL_Booking_App.repository;

import KL.KL_Booking_App.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
