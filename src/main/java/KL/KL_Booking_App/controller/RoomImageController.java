package KL.KL_Booking_App.controller;

import KL.KL_Booking_App.constants.Global;
import KL.KL_Booking_App.constants.api.RoomApi;
import KL.KL_Booking_App.payload.response.Response;
import KL.KL_Booking_App.payload.response.RoomImageDto;
import KL.KL_Booking_App.service.IRoomImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("api/v1")
public class RoomImageController {

    private final IRoomImageService roomImageService;

    public RoomImageController(IRoomImageService roomImageService) {
        this.roomImageService = roomImageService;
    }

    @PostMapping(RoomApi.ROOM_BY_ID + "/upload")
    public ResponseEntity<Response> uploadImage(@PathVariable(value = "roomId")Long roomId , @RequestParam("file")MultipartFile file){
        try {
            RoomImageDto roomImageDto = roomImageService.uploadImage(file, roomId);
            return new ResponseEntity<>(
                    Response.builder()
                            .status(Global.STATUS_SUCCESS)
                            .data(roomImageDto)
                            .message(Global.MESSAGE_CREATED_SUCCESSFULLY)
                            .time(new SimpleDateFormat("dd-MM-yyyy").format(new Date()))
                            .build()
                    , HttpStatus.CREATED
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    Response.builder()
                            .status(Global.STATUS_FAILED)
                            .message(Global.MESSAGE_CREATED_FAILED)
                            .time(new SimpleDateFormat("dd-MM-yyyy").format(new Date()))
                            .build()
                    , HttpStatus.BAD_REQUEST);
        }
    }
}
