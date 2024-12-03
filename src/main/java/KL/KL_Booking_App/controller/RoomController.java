package KL.KL_Booking_App.controller;

import KL.KL_Booking_App.constants.Global;
import KL.KL_Booking_App.constants.api.HotelApi;
import KL.KL_Booking_App.constants.api.RoomApi;
import KL.KL_Booking_App.payload.response.Response;
import KL.KL_Booking_App.payload.response.RoomDto;
import KL.KL_Booking_App.service.IRoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@RequestMapping("/api/v1")
@RestController
public class RoomController {
    private static final Logger log = LoggerFactory.getLogger(RoomController.class);

    private final IRoomService roomService;


    public RoomController(IRoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping(RoomApi.ROOM_BY_ID)
    public ResponseEntity<Response> getRoomById(@PathVariable(value = "roomId") Long roomId){
        try{
        RoomDto roomDto = roomService.getRoomById(roomId);
        Response response = new Response(
                Global.STATUS_SUCCESS,
                roomDto, Global.MESSAGE_GET_SUCCESSFULLY );
        return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            // Handle error, create failure response
            Response response = new Response();
            response.setStatus(Global.STATUS_FAILED);
            response.setMessage(e.getMessage());
            // Set current date formatted in dd-MM-yyyy
            response.setTime(new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
            // Log the exception with error level
            log.error("Error creating new room", e);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(RoomApi.ROOMS)
    public ResponseEntity<Response> getAllRooms(){
        List<RoomDto> roomDtos = roomService.getAllRooms();
        Response response = new Response(
                Global.STATUS_SUCCESS,
                roomDtos, Global.MESSAGE_GET_SUCCESSFULLY );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(HotelApi.HOTEL_BY_ID + RoomApi.ROOMS)
    public ResponseEntity<Response> createANewRoom(@PathVariable(value = "hotelId") Long hotelId,@RequestBody RoomDto roomDto){
        try {
            // Call service to create a new room
            RoomDto roomDtoResponse = roomService.createANewRoom(hotelId, roomDto);
            // Prepare successful response
            Response response = new Response(
                    Global.STATUS_SUCCESS,
                    roomDtoResponse,
                    Global.MESSAGE_CREATED_SUCCESSFULLY);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            // Handle error, create failure response
            Response response = new Response();
            response.setStatus(Global.STATUS_FAILED);
            response.setMessage(e.getMessage());
            // Set current date formatted in dd-MM-yyyy
            response.setTime(new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
            // Log the exception with error level
            log.error("Error creating new room", e);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

}
