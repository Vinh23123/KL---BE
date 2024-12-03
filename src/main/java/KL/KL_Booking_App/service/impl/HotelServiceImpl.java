package KL.KL_Booking_App.service.impl;

import KL.KL_Booking_App.entity.Hotel;
import KL.KL_Booking_App.exeption.ResourceNotFoundException;
import KL.KL_Booking_App.repository.HotelRepository;
import KL.KL_Booking_App.service.IHotelService;
import org.springframework.stereotype.Service;

@Service
public class HotelServiceImpl implements IHotelService {

     private final HotelRepository hotelRepository;

    public HotelServiceImpl(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }


    @Override
    public Hotel findHotelById(Long hotelId) {
        return hotelRepository.findById(hotelId).orElseThrow(() -> new ResourceNotFoundException("Hotel", "Id", hotelId));
    }
}
