package KL.KL_Booking_App.controller;

import KL.KL_Booking_App.constants.Global;
import KL.KL_Booking_App.constants.api.RoomApi;
import KL.KL_Booking_App.entity.Room;
import KL.KL_Booking_App.payload.response.Response;
import KL.KL_Booking_App.payload.response.RoomDto;
import KL.KL_Booking_App.service.IRoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RequestMapping("/api/v1")
@RestController
public class RoomController {
    private final IRoomService roomService;


    public RoomController(IRoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping(RoomApi.ROOM_BY_ID)
    public ResponseEntity<Response> getRoomById(@PathVariable(value = "roomId") Long roomId){

        RoomDto roomDto = roomService.getRoomById(roomId);
        Response response = new Response(
                Global.STATUS_SUCCESS,
                roomDto, Global.MESSAGE_SUCCESS );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(RoomApi.ROOMS)
    public ResponseEntity<Response> getAllRooms(){

        List<RoomDto> roomDtos = roomService.getAllRooms();
        Response response = new Response(
                Global.STATUS_SUCCESS,
                roomDtos, Global.MESSAGE_SUCCESS );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
