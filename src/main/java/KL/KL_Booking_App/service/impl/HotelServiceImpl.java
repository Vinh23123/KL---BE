package KL.KL_Booking_App.service.impl;

import KL.KL_Booking_App.entity.Hotel;
import KL.KL_Booking_App.exeption.ResourceNotFoundException;
import KL.KL_Booking_App.payload.response.HotelDto;
import KL.KL_Booking_App.repository.HotelRepository;
import KL.KL_Booking_App.service.IHotelService;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<HotelDto> getAll() {
        List<Hotel> hotels = hotelRepository.findAll();
        return hotels.stream().map(this::mapToDto).toList();
    }

    @Override
    public HotelDto save(HotelDto hotelDto) {
        Hotel hotel =  mapToEntity(hotelDto);
        Hotel savedhotel = hotelRepository.save(hotel);
        return mapToDto(savedhotel);
    }

    @Override
    public void delete(Hotel hotel) {
        hotelRepository.delete(hotel);
    }

    @Override
    public HotelDto update(HotelDto hotelDto){
        Hotel hotel = findHotelById(hotelDto.getHotelId());
        Hotel updatedhotel = hotelRepository.save(hotel);
        return mapToDto(updatedhotel);
    }

    private HotelDto mapToDto(Hotel hotel){
        return HotelDto.builder()
                .phoneNumber(hotel.getPhoneNumber())
                .email(hotel.getEmail())
                .description(hotel.getDescription())
                .build();
    }

    private Hotel mapToEntity(HotelDto hotelDto){
        return Hotel.builder()
                .phoneNumber(hotelDto.getPhoneNumber())
                .email(hotelDto.getEmail())
                .description(hotelDto.getDescription())
                .build();
    }

}
