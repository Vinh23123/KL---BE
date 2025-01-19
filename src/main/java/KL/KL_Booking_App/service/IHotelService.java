package KL.KL_Booking_App.service;

import KL.KL_Booking_App.entity.Hotel;
import KL.KL_Booking_App.payload.response.HotelDto;

import java.util.List;

public interface IHotelService {
   Hotel findHotelById(Long hotelId);

   List<HotelDto> getAll();

   HotelDto save(HotelDto hotelDto);

   void delete(HotelDto hotelDto);

   HotelDto update(HotelDto hotelDto);

   HotelDto fetchCurrentHotel();
}
