package KL.KL_Booking_App.controller;

import KL.KL_Booking_App.constants.Global;
import KL.KL_Booking_App.constants.api.HotelApi;
import KL.KL_Booking_App.constants.api.LocationApi;
import KL.KL_Booking_App.payload.response.LocationDto;
import KL.KL_Booking_App.payload.response.Response;
import KL.KL_Booking_App.service.ILocationService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("api/v1")
public class LocationController {

    private static final Logger log = LoggerFactory.getLogger(LocationController.class);
    private final ILocationService locationService;

    public LocationController(ILocationService locationService) {
        this.locationService = locationService;
    }

    @PostMapping(HotelApi.HOTEL_BY_ID + LocationApi.LOCATIONS)
    public ResponseEntity<Response> createALocationWithHotel(@PathVariable(value = "hotelId") Long hotelId, @Valid @RequestBody LocationDto locationDto){

        try {
            LocationDto savedLocation = locationService.save(hotelId, locationDto);
            return new ResponseEntity<>( Response.builder()
                    .status(Global.STATUS_SUCCESS)
                    .data(savedLocation)
                    .message(Global.MESSAGE_CREATED_SUCCESSFULLY)
                    .time(new SimpleDateFormat("dd-MM-yyyy").format(new Date()))
                    .build(), HttpStatus.CREATED);
        }catch (Exception e){
            log.info(e.getMessage());
            return new ResponseEntity<>( Response.builder()
                    .status(Global.STATUS_FAILED)
                    .message(e.getMessage())
                    .time(new SimpleDateFormat("dd-MM-yyyy").format(new Date()))
                    .build(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(HotelApi.HOTEL_BY_ID + LocationApi.LOCATIONS)
    public ResponseEntity<Response> updateLocation(@PathVariable(value = "hotelId") Long hotelId,@Valid @RequestBody LocationDto locationDto){

        try {
            LocationDto updatedLocation = locationService.update(hotelId ,locationDto);
            return new ResponseEntity<>( Response.builder()
                    .status(Global.STATUS_SUCCESS)
                    .data(updatedLocation)
                    .message(Global.MESSAGE_UPDATED_SUCCESSFULLY)
                    .time(new SimpleDateFormat("dd-MM-yyyy").format(new Date()))
                    .build(), HttpStatus.OK);
        }catch (Exception e){
            log.info(e.getMessage());
            return new ResponseEntity<>( Response.builder()
                    .status(Global.STATUS_FAILED)
                    .message(e.getMessage())
                    .time(new SimpleDateFormat("dd-MM-yyyy").format(new Date()))
                    .build(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(LocationApi.LOCATIONS + "/convert-location")
    public ResponseEntity<Response> convertLocationToAddress(@Valid @RequestBody LocationDto locationDto){

        try {
        LocationDto address = locationService.convertLatLongToAddress(locationDto);
            return new ResponseEntity<>( Response.builder()
                    .status(Global.STATUS_SUCCESS)
                    .data(address)
                    .message(Global.MESSAGE_GET_SUCCESSFULLY)
                    .time(new SimpleDateFormat("dd-MM-yyyy").format(new Date()))
                    .build(), HttpStatus.CREATED);
        }catch (Exception e){
            log.info(e.getMessage());
            return new ResponseEntity<>( Response.builder()
                    .status(Global.STATUS_FAILED)
                    .message(e.getMessage())
                    .time(new SimpleDateFormat("dd-MM-yyyy").format(new Date()))
                    .build(), HttpStatus.NOT_FOUND);
        }
    }

}
