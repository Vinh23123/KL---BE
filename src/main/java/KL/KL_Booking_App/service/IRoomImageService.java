package KL.KL_Booking_App.service;

import KL.KL_Booking_App.entity.RoomImage;
import KL.KL_Booking_App.payload.response.RoomImageDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IRoomImageService {
    List<RoomImageDto> uploadImage(List<MultipartFile> files, Long roomId) throws IOException;
    void deleteRoomImage(Long roomImageId);
}
