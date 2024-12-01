package KL.KL_Booking_App.service.impl;

import KL.KL_Booking_App.entity.Room;
import KL.KL_Booking_App.exeption.ResourceNotFoundException;
import KL.KL_Booking_App.repository.RoomRepository;
import KL.KL_Booking_App.service.IRoomService;
import org.springframework.stereotype.Service;

@Service
public class RoomServiceImpl implements IRoomService {

    private final RoomRepository roomRepository;

    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }


    @Override
    public Room getRoomById(Long roomId) {
        // return dto later
        return roomRepository.findById(roomId).orElseThrow(() -> new ResourceNotFoundException("Room", "Id", roomId));
    }
}
