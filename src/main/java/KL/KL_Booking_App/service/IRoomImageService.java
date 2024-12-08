package KL.KL_Booking_App.service;

import KL.KL_Booking_App.entity.RoomImage;
import KL.KL_Booking_App.payload.response.RoomImageDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IRoomImageService {
    RoomImageDto uploadImage(MultipartFile file, Long roomId) throws IOException;
    List<RoomImageDto> getAllRoomImages();
    RoomImageDto updateRoomImage(RoomImageDto roomImageDto);
    RoomImageDto fetchRoomImage(Long roomImageId);
    void deleteRoomImage(Long roomImageId);
}
