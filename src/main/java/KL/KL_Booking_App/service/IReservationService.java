package KL.KL_Booking_App.service;

import KL.KL_Booking_App.entity.Reservation;
import KL.KL_Booking_App.payload.response.ReservationDto;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface IReservationService {
    ReservationDto createReservation(Long roomId, Long discountId, ReservationDto reservationDto);
    Reservation getReservationById(Long reservationId);
    void deleteReservation(Long reservationId);
    List<ReservationDto> getAllReservations();
    ReservationDto updateReservation(Long roomId ,Long discountId, ReservationDto reservationDto);
}
