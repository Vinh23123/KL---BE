package KL.KL_Booking_App.service.impl;

import KL.KL_Booking_App.entity.Hotel;
import KL.KL_Booking_App.entity.User;
import KL.KL_Booking_App.exeption.ResourceNotFoundException;
import KL.KL_Booking_App.payload.response.HotelDto;
import KL.KL_Booking_App.repository.HotelRepository;
import KL.KL_Booking_App.repository.UserRepository;
import KL.KL_Booking_App.service.IHotelService;
import KL.KL_Booking_App.utils.HotelUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HotelServiceImpl implements IHotelService {

    private final HotelRepository hotelRepository;
    private final HotelUtils hotelUtils;
    private final UserRepository userRepository;

    public HotelServiceImpl(HotelRepository hotelRepository, HotelUtils hotelUtils, UserRepository userRepository) {
        this.hotelRepository = hotelRepository;
        this.hotelUtils = hotelUtils;
        this.userRepository = userRepository;
    }

    @Override
    public Hotel findHotelById(Long hotelId) {

        return hotelRepository.findById(hotelId).orElseThrow(() -> new ResourceNotFoundException("Hotel", "Id", hotelId));
    }

    @Override
    public List<HotelDto> getAll() {
        long userId = 1;
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

        List<Hotel> reservations = hotelRepository.findByUserUserId(user.getUserId());
        return reservations.stream().map(hotelUtils::mapToDto).collect(Collectors.toList());
    }

    @Override
    public HotelDto save(HotelDto hotelDto) {
        // retrieve current user id to add hotel
        // retrieve current id user -> get all reservations
        // Fake user id 1
        long userId = 1;
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

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

    @Override
    public HotelDto fetchCurrentHotel() {
        long userId = 1;
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        Hotel hotel = user.getHotel();
        return hotelUtils.mapToDto(hotel);
    }


}
