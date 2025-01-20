package KL.KL_Booking_App.utils;

import KL.KL_Booking_App.entity.Reservation;
import KL.KL_Booking_App.entity.Room;
import KL.KL_Booking_App.entity.User;
import KL.KL_Booking_App.payload.response.ReservationDto;
import KL.KL_Booking_App.payload.response.UserDto;
import org.springframework.stereotype.Component;

@Component

public class ReservationUtils {
    public ReservationDto mapToReservationDto(Reservation reservation){
        return ReservationDto
                .builder()
                .reservationId(reservation.getReservationId())
//                .reservationType(reservation.getReservationType())
                .checkIn(reservation.getCheckIn())
                .checkOut(reservation.getCheckOut())
                .totalAmount(reservation.getTotalAmount())
                .payment(reservation.getPayment())
                .build();
    }
    public ReservationDto mapToReservationDtoIncludedUser(Reservation reservation){
        return ReservationDto
                .builder()
                .reservationId(reservation.getReservationId())
//                .reservationType(reservation.getReservationType())
                .checkIn(reservation.getCheckIn())
                .checkOut(reservation.getCheckOut())
                .totalAmount(reservation.getTotalAmount())
                .userDto(mapToUserDto(reservation.getUser()))
                .build();
    }

    public UserDto mapToUserDto(User user){
        return UserDto.builder()
                .userId(user.getUserId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phone(user.getPhone())
                .mail(user.getMail())
                .build();
    }
}
