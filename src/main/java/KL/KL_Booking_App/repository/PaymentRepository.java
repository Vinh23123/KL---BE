package KL.KL_Booking_App.repository;

import KL.KL_Booking_App.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Payment findByVnpTransactionRef(String VnpTransactionRef);
}
