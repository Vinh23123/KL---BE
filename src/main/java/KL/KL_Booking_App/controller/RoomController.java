package KL.KL_Booking_App.controller;

import KL.KL_Booking_App.constants.Global;
import KL.KL_Booking_App.constants.api.RoomApi;
import KL.KL_Booking_App.entity.Room;
import KL.KL_Booking_App.payload.response.ErrorDetails;
import KL.KL_Booking_App.payload.response.Response;
import KL.KL_Booking_App.payload.response.RoomDto;
import KL.KL_Booking_App.service.IRoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
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

        RoomDto roomDto = roomService.getRoomById(roomId);
        Response response = new Response(
                Global.STATUS_SUCCESS,
                roomDto, Global.MESSAGE_GET_SUCCESSFULLY );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(RoomApi.ROOMS)
    public ResponseEntity<Response> getAllRooms(){

        List<RoomDto> roomDtos = roomService.getAllRooms();
        Response response = new Response(
                Global.STATUS_SUCCESS,
                roomDtos, Global.MESSAGE_GET_SUCCESSFULLY );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(RoomApi.ROOMS)
    public ResponseEntity<Response> createANewRoom(@RequestBody(required = true) RoomDto roomDto){

        try {
            RoomDto roomDtoResponse = roomService.createANewRoom(roomDto);
            Response response = new Response(
                    Global.STATUS_SUCCESS,
                    roomDtoResponse,
                    Global.MESSAGE_CREATED_SUCCESSFULLY);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e){
            Response response = new Response();
            response.setStatus(Global.STATUS_FAILED);
            response.setMessage(Global.MESSAGE_CREATED_FAILED);
            response.setTime(String.valueOf(new SimpleDateFormat("dd-MM-yyyy")));
            log.info(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

    }
}
