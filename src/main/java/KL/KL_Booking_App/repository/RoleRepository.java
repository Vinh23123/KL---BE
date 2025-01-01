package KL.KL_Booking_App.repository;

import KL.KL_Booking_App.entity.Role;
import KL.KL_Booking_App.entity.roleType.ERole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByName(ERole name);
}
