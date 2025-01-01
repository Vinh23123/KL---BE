package KL.KL_Booking_App.controller;

import KL.KL_Booking_App.constants.Global;
import KL.KL_Booking_App.constants.api.RoomApi;
import KL.KL_Booking_App.constants.api.RoomImageApi;
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
import java.util.List;

@RestController
@RequestMapping("api/v1")
@CrossOrigin(origins = "http://localhost:5173")
public class RoomImageController {

    private final IRoomImageService roomImageService;

    public RoomImageController(IRoomImageService roomImageService) {
        this.roomImageService = roomImageService;
    }

    @PostMapping(RoomApi.ROOM_BY_ID + "/upload")
    public ResponseEntity<Response> uploadImage(@PathVariable(value = "roomId")Long roomId , @RequestParam("file") List<MultipartFile> files){
        try {
            // Check if the number of files is valid
            if (files.size() > 5) {
                throw new IllegalArgumentException("Cannot upload more than 5 images.");
            }
            List<RoomImageDto> roomImageDtos = roomImageService.uploadImage(files, roomId);
            return new ResponseEntity<>(
                    Response.builder()
                            .status(Global.STATUS_SUCCESS)
                            .data(roomImageDtos)
                            .message(Global.MESSAGE_CREATED_SUCCESSFULLY)
                            .time(new SimpleDateFormat("dd-MM-yyyy").format(new Date()))
                            .build()
                    , HttpStatus.CREATED
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    Response.builder()
                            .status(Global.STATUS_FAILED)
                            .message(e.getMessage())
                            .time(new SimpleDateFormat("dd-MM-yyyy").format(new Date()))
                            .build()
                    , HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(RoomImageApi.ROOM_IMAGES_BY_ID)
    public ResponseEntity<Response> deleteImage(@PathVariable(value = "roomImageId")Long roomId){
        try {
            roomImageService.deleteRoomImage(roomId);
            return new ResponseEntity<>(
                    Response.builder()
                            .status(Global.STATUS_SUCCESS)
                            .message(Global.MESSAGE_DELETED_SUCCESSFULLY)
                            .time(new SimpleDateFormat("dd-MM-yyyy").format(new Date()))
                            .build()
                    , HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    Response.builder()
                            .status(Global.STATUS_FAILED)
                            .message(e.getMessage())
                            .time(new SimpleDateFormat("dd-MM-yyyy").format(new Date()))
                            .build()
                    , HttpStatus.BAD_REQUEST);
        }
    }
}
