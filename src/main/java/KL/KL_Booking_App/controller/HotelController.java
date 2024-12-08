package KL.KL_Booking_App.controller;

import KL.KL_Booking_App.constants.Global;
import KL.KL_Booking_App.constants.api.HotelApi;
import KL.KL_Booking_App.entity.Hotel;
import KL.KL_Booking_App.payload.response.HotelDto;
import KL.KL_Booking_App.payload.response.Response;
import KL.KL_Booking_App.service.IHotelService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("api/v1")
public class HotelController {

    private final IHotelService hotelService;

    public HotelController(IHotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping(HotelApi.HOTEL_BY_ID)
    public ResponseEntity<Response> fetchHotelById(@PathVariable(value = "hotelId") Long hotelId){
        try {
            Hotel hotel = hotelService.findHotelById(hotelId);
            return new ResponseEntity<>(
                    Response.builder()
                            .status(Global.STATUS_SUCCESS)
                            .data(hotel)
                            .message(Global.MESSAGE_GET_SUCCESSFULLY)
                            .time(new SimpleDateFormat("dd-MM-yyyy").format(new Date()))
                            .build(),
                    HttpStatus.OK
            );
        } catch (Exception e){
            return new ResponseEntity<>(
                    Response.builder()
                            .status(Global.STATUS_FAILED)
                            .message(e.getMessage())
                            .time(new SimpleDateFormat("dd-MM-yyyy").format(new Date()))
                            .build(),
                    HttpStatus.NOT_FOUND
            );
        }
    }

    @PutMapping(HotelApi.HOTELS)
    public ResponseEntity<Response> updateHotelById(@Valid @RequestBody HotelDto hotelDto){
        try {
            HotelDto updatehotelDto = hotelService.update(hotelDto);
            return new ResponseEntity<>(
                    Response.builder()
                            .status(Global.STATUS_SUCCESS)
                            .data(updatehotelDto)
                            .message(Global.MESSAGE_UPDATED_SUCCESSFULLY)
                            .time(new SimpleDateFormat("dd-MM-yyyy").format(new Date()))
                            .build(),
                    HttpStatus.OK
            );
        } catch (Exception e){
            return new ResponseEntity<>(
                    Response.builder()
                            .status(Global.STATUS_FAILED)
                            .message(e.getMessage())
                            .time(new SimpleDateFormat("dd-MM-yyyy").format(new Date()))
                            .build(),
                    HttpStatus.NOT_FOUND
            );
        }
    }

    @PostMapping(HotelApi.HOTELS)
    public ResponseEntity<Response> createANewHotel(@Valid @RequestBody HotelDto hotelDto){
        try {
            HotelDto createdHotel = hotelService.save(hotelDto);
            return new ResponseEntity<>(
                    Response.builder()
                            .status(Global.STATUS_SUCCESS)
                            .data(createdHotel)
                            .message(Global.MESSAGE_CREATED_SUCCESSFULLY)
                            .time(new SimpleDateFormat("dd-MM-yyyy").format(new Date()))
                            .build(),
                    HttpStatus.OK
            );
        } catch (Exception e){
            return new ResponseEntity<>(
                    Response.builder()
                            .status(Global.STATUS_FAILED)
                            .message(e.getMessage())
                            .time(new SimpleDateFormat("dd-MM-yyyy").format(new Date()))
                            .build(),
                    HttpStatus.NOT_FOUND
            );
        }
    }

    @DeleteMapping(HotelApi.HOTEL_BY_ID)
    public ResponseEntity<Response> deleteHotel(@Valid @RequestBody HotelDto hotelDto){
        try {
            hotelService.delete(hotelDto);
            return new ResponseEntity<>(
                    Response.builder()
                            .status(Global.STATUS_SUCCESS)
                            .message(Global.MESSAGE_DELETED_SUCCESSFULLY)
                            .time(new SimpleDateFormat("dd-MM-yyyy").format(new Date()))
                            .build(),
                    HttpStatus.OK
            );
        } catch (Exception e){
            return new ResponseEntity<>(
                    Response.builder()
                            .status(Global.STATUS_FAILED)
                            .message(e.getMessage())
                            .time(new SimpleDateFormat("dd-MM-yyyy").format(new Date()))
                            .build(),
                    HttpStatus.NOT_FOUND
            );
        }
    }
}
