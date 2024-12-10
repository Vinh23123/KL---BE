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
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.cloudinary.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.http.HttpClient;

@Service
public class LocationServiceImpl implements ILocationService {

    private static final Logger log = LoggerFactory.getLogger(LocationServiceImpl.class);
    private final LocationRepository locationRepository;

    private static final String API_KEY_GOONG = System.getenv("API_KEY");

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

    @Override
    public LocationDto convertLatLongToAddress(LocationDto locationDto) {
        String url = "https://rsapi.goong.io/geocode?latlng=" + locationDto.getLatitude() + "," + locationDto.getLongitude() + "&api_key=" + API_KEY_GOONG;

        HttpGet httpGet = new HttpGet(url);
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(httpGet)) {
                HttpEntity httpEntity = response.getEntity();
                String responseString = EntityUtils.toString(httpEntity);
                JSONObject jsonObjectRes = new JSONObject(responseString);

                // Check if the status is "OK"
                if (jsonObjectRes.getString("status").equals("OK")) {
                    // Check if there are any results
                    if (jsonObjectRes.getJSONArray("results").length() > 0) {
                        String formattedAddress = jsonObjectRes.getJSONArray("results")
                                .getJSONObject(0)
                                .getString("formatted_address");

                        double latitude = jsonObjectRes.getJSONArray("results")
                                .getJSONObject(0)
                                .getJSONObject("geometry").getJSONObject("location").getDouble("lat");

                        double longitude = jsonObjectRes.getJSONArray("results")
                                .getJSONObject(0)
                                .getJSONObject("geometry").getJSONObject("location").getDouble("lng");

                        log.info("Address: " + formattedAddress);

                        // Return the LocationDto with the address
                        return LocationDto.builder()
                                .latitude(latitude)
                                .longitude(longitude)
                                .formattedAddress(formattedAddress)
                                .build();
                    } else {
                        log.warn("No results found for the given coordinates.");
                    }
                } else {
                    log.error("Error from Goong API: " + jsonObjectRes.getString("message"));
            }
        } catch (IOException e) {
            log.error("IOException occurred while calling Goong API: " + e.getMessage());
            throw new RuntimeException("Error occurred while retrieving address", e);
        }

        // Return the original locationDto in case of failure or empty result
        return locationDto;
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
