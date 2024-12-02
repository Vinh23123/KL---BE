package KL.KL_Booking_App.service.impl;

import KL.KL_Booking_App.entity.RoomImage;
import KL.KL_Booking_App.repository.RoomImageRepository;
import KL.KL_Booking_App.service.IRoomImageService;
import org.springframework.stereotype.Service;

@Service
public class RoomImageServiceImpl implements IRoomImageService {

    private final RoomImageRepository roomImageRepository;

    public RoomImageServiceImpl(RoomImageRepository roomImageRepository) {
        this.roomImageRepository = roomImageRepository;
    }


    @Override
    public void saveRoomImage(RoomImage roomImage) {
        roomImageRepository.save(roomImage);
    }
}
