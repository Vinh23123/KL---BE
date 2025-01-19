package KL.KL_Booking_App.service;

import KL.KL_Booking_App.payload.response.LocationDto;

public interface ILocationService {
    LocationDto get(Long locationId);
    LocationDto save(Long hotelId, LocationDto locationDto);
    LocationDto update(Long hotelId, LocationDto locationDto);
    LocationDto convertLatLongToAddress(LocationDto locationDto);
    LocationDto convertAddressToLatLong(LocationDto locationDto);
}
