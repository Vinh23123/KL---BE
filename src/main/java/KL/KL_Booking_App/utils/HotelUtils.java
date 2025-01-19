package KL.KL_Booking_App.utils;

import KL.KL_Booking_App.entity.Hotel;
import KL.KL_Booking_App.payload.response.HotelDto;
import org.springframework.stereotype.Component;

@Component
public class HotelUtils {

    public HotelDto mapToDto(Hotel hotel){
        return HotelDto.builder()
                .hotelId(hotel.getHotelId())
                .hotelName(hotel.getHotelName())
                .phoneNumber(hotel.getPhoneNumber())
                .email(hotel.getEmail())
                .description(hotel.getDescription())
                .location(hotel.getLocation())
                .build();
    }

    public Hotel mapToEntity(HotelDto hotelDto){
        // FAKE USER ID to add user : NOTE
        return Hotel.builder()
                .hotelName(hotelDto.getHotelName())
                .phoneNumber(hotelDto.getPhoneNumber())
                .email(hotelDto.getEmail())
                .description(hotelDto.getDescription())
                .build();
    }
}
