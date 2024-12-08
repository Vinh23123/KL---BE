package KL.KL_Booking_App.service.impl;

import KL.KL_Booking_App.entity.Hotel;
import KL.KL_Booking_App.exeption.ResourceNotFoundException;
import KL.KL_Booking_App.payload.response.HotelDto;
import KL.KL_Booking_App.repository.HotelRepository;
import KL.KL_Booking_App.service.IHotelService;
import KL.KL_Booking_App.utils.HotelUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelServiceImpl implements IHotelService {

    private final HotelRepository hotelRepository;
    private final HotelUtils hotelUtils;

    public HotelServiceImpl(HotelRepository hotelRepository, HotelUtils hotelUtils) {
        this.hotelRepository = hotelRepository;
        this.hotelUtils = hotelUtils;
    }

    @Override
    public Hotel findHotelById(Long hotelId) {
        return hotelRepository.findById(hotelId).orElseThrow(() -> new ResourceNotFoundException("Hotel", "Id", hotelId));
    }

    @Override
    public List<HotelDto> getAll() {
        List<Hotel> hotels = hotelRepository.findAll();
        return hotels.stream().map(hotelUtils::mapToDto).toList();
    }

    @Override
    public HotelDto save(HotelDto hotelDto) {
        Hotel hotel =  hotelUtils.mapToEntity(hotelDto);
        Hotel savedhotel = hotelRepository.save(hotel);
        return hotelUtils.mapToDto(savedhotel);
    }

    @Override
    public void delete(HotelDto hotelDto) {
        Hotel hotel = hotelUtils.mapToEntity(hotelDto);
        hotelRepository.delete(hotel);
    }

    @Override
    public HotelDto update(HotelDto hotelDto){
        Hotel hotel = findHotelById(hotelDto.getHotelId());
        hotel.setHotelName(hotelDto.getHotelName());
        hotel.setPhoneNumber(hotelDto.getPhoneNumber());
        hotel.setEmail(hotelDto.getEmail());
        hotel.setDescription(hotelDto.getDescription());

        Hotel updatedhotel = hotelRepository.save(hotel);
        return hotelUtils.mapToDto(updatedhotel);
    }


}
