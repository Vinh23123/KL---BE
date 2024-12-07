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
        return new ResponseEntity<>(
                Response.builder()
                        .status(Global.STATUS_SUCCESS)
                        .data(roomDto)
                        .message(Global.MESSAGE_GET_SUCCESSFULLY )
                        .time(new SimpleDateFormat("dd-MM-yyyy").format(new Date()))
                        .build()
                , HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error creating new room", e);
            return new ResponseEntity<>(
                    Response.builder()
                            .status(Global.STATUS_FAILED)
                            .message(e.getMessage())
                            .time(new SimpleDateFormat("dd-MM-yyyy").format(new Date()))
                            .build()
                    , HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(HotelApi.HOTEL_BY_ID + RoomApi.ROOMS)
    public ResponseEntity<Response> fetchAllRoomsByHotelId(@PathVariable(value = "hotelId") Long hotelId){
        List<RoomDto> roomDtos = roomService.getAllRoomsByHotelId(hotelId);
        return new ResponseEntity<>(
                Response.builder()
                        .status(Global.STATUS_SUCCESS)
                        .data(roomDtos)
                        .message(Global.MESSAGE_GET_SUCCESSFULLY)
                        .time(new SimpleDateFormat("dd-MM-yyyy").format(new Date()))
                        .build()
                , HttpStatus.OK);
    }

    @PostMapping(HotelApi.HOTEL_BY_ID + RoomApi.ROOMS)
    public ResponseEntity<Response> createANewRoom(@PathVariable(value = "hotelId") Long hotelId,@RequestBody RoomDto roomDto){
        try {
            // Call service to create a new room
            RoomDto roomDtoResponse = roomService.createANewRoom(hotelId, roomDto);
            return new ResponseEntity<>(
                    Response.builder()
                            .status(Global.STATUS_SUCCESS)
                            .data(roomDtoResponse)
                            .message(Global.MESSAGE_CREATED_SUCCESSFULLY)
                            .time(new SimpleDateFormat("dd-MM-yyyy").format(new Date()))
                            .build()
                    , HttpStatus.CREATED);
        } catch (Exception e) {
            // Log the exception with error level
            log.error("Error creating new room", e);
            return new ResponseEntity<>(
                    Response.builder()
                            .status(Global.STATUS_FAILED)
                            .message(e.getMessage())
                            .time(new SimpleDateFormat("dd-MM-yyyy").format(new Date()))
                            .build()
                    , HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(RoomApi.ROOMS)
    public ResponseEntity<Response> updateRoomById(@RequestBody RoomDto roomDto){
        try {
            // Call service to create a new room
            RoomDto roomDtoResponse = roomService.updateRoomById(roomDto);
            // Prepare successful response
            return new ResponseEntity<>(
                    Response.builder()
                            .status(Global.STATUS_SUCCESS)
                            .data(roomDtoResponse)
                            .message(Global.MESSAGE_CREATED_SUCCESSFULLY)
                            .build()
                    ,HttpStatus.CREATED);
        } catch (Exception e) {
            // Log the exception with error level
            log.error("Error creating new room", e);
            return new ResponseEntity<>(
                    Response.builder()
                            .status(Global.STATUS_FAILED)
                            .message(e.getMessage())
                            .time(new SimpleDateFormat("dd-MM-yyyy").format(new Date()))
                            .build()
                    , HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(RoomApi.ROOM_BY_ID)
    public ResponseEntity<Response> deleteRoomById(@PathVariable(value = "roomId") Long roomId){
        try{
            roomService.deleteRoomById(roomId);
            return new ResponseEntity<>(Response.builder()
                    .status(Global.STATUS_SUCCESS)
                    .message(Global.MESSAGE_DELETED_SUCCESSFULLY)
                    .time(new SimpleDateFormat("dd-MM-yyyy").format(new Date()))
                    .build(), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error delete a room", e);
            return new ResponseEntity<>(
                    Response.builder()
                            .status(Global.STATUS_FAILED)
                            .message(e.getMessage())
                            .time(new SimpleDateFormat("dd-MM-yyyy").format(new Date()))
                            .build()
                    ,HttpStatus.NOT_FOUND);
        }
    }

}
