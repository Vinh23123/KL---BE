package KL.KL_Booking_App.service;

import KL.KL_Booking_App.entity.Hotel;

public interface IHotelService {
    Hotel findHotelById(Long hotelId);
}
