package KL.KL_Booking_App.controller;

import KL.KL_Booking_App.constants.Global;
import KL.KL_Booking_App.entity.Room;
import KL.KL_Booking_App.payload.response.Response;
import KL.KL_Booking_App.service.IRoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


//@RequestMapping("/api/v1")
@RestController
public class RoomController {
    private final IRoomService roomService;


    public RoomController(IRoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/api/v1/rooms/{roomId}")
    public ResponseEntity<Response> getRoomById(@PathVariable(value = "roomId") Long roomId){

        Room room = roomService.getRoomById(roomId);
        Response response = new Response(
                Global.STATUS_SUCCESS,
                room, Global.MESSAGE_SUCCESS );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
