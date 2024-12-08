package KL.KL_Booking_App.service.impl;

import KL.KL_Booking_App.entity.Hotel;
import KL.KL_Booking_App.entity.Location;
import KL.KL_Booking_App.exeption.ResourceNotFoundException;
import KL.KL_Booking_App.payload.response.HotelDto;
import KL.KL_Booking_App.payload.response.LocationDto;
import KL.KL_Booking_App.repository.LocationRepository;
import KL.KL_Booking_App.service.IHotelService;
import KL.KL_Booking_App.service.ILocationService;
import KL.KL_Booking_App.utils.HotelUtils;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class LocationServiceImpl implements ILocationService {

    private final LocationRepository locationRepository;

    private final IHotelService hotelService;
    private final HotelUtils hotelUtils;

    public LocationServiceImpl(LocationRepository locationRepository, IHotelService hotelService, HotelUtils hotelUtils) {
        this.locationRepository = locationRepository;
        this.hotelService = hotelService;
        this.hotelUtils = hotelUtils;
    }

    @Override
    public LocationDto get(Long locationId) {
        Location location = locationRepository.findById(locationId).orElseThrow(() -> new ResourceNotFoundException("Location", "Id", locationId));
        return mapToDto(location);
    }

    @Transactional
    @Override
    public LocationDto save(Long hotelId, LocationDto locationDto) {
        Location location = mapToEntity(locationDto);
        Hotel hotel = hotelService.findHotelById(hotelId);
        location.setHotel(hotel);
        locationRepository.save(location);
        return mapToDto(location);
    }

    @Transactional
    @Override
    public LocationDto update(Long hotelId ,LocationDto locationDto) {
        Location location = mapToEntity(locationDto);
        Hotel hotel = hotelService.findHotelById(hotelId);
        location.setLatitude(locationDto.getLatitude());
        location.setLongitude(locationDto.getLongitude());
        location.setHotel(hotel);
        locationRepository.save(location);
        return mapToDto(location);
    }

    private LocationDto mapToDto(Location location){
        return LocationDto
                .builder()
                .locationId(location.getLocationId())
                .latitude(location.getLatitude())
                .longitude(location.getLongitude())
                .build();
    }

    private Location mapToEntity(LocationDto locationDto){
        return Location
                .builder()
                .locationId(locationDto.getLocationId())
                .latitude(locationDto.getLatitude())
                .longitude(locationDto.getLongitude())
                .build();
    }

    private HotelDto mapHotelToDto(Hotel hotel){
        return HotelDto.builder()
                .hotelId(hotel.getHotelId())
                .hotelName(hotel.getHotelName())
                .phoneNumber(hotel.getPhoneNumber())
                .email(hotel.getEmail())
                .description(hotel.getDescription())
                .build();
    }
}
