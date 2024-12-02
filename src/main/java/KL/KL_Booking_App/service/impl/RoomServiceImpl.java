package KL.KL_Booking_App.service.impl;

import KL.KL_Booking_App.entity.Room;
import KL.KL_Booking_App.exeption.ResourceNotFoundException;
import KL.KL_Booking_App.payload.response.RoomDto;
import KL.KL_Booking_App.repository.RoomRepository;
import KL.KL_Booking_App.service.IRoomService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomServiceImpl implements IRoomService {

    private final RoomRepository roomRepository;

    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }


    @Override
    public RoomDto getRoomById(Long roomId) {
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new ResourceNotFoundException("Room", "Id", roomId));
        // return dto later
        return mapToDto(room);
    }

    @Override
    public List<RoomDto> getAllRooms() {
        List<Room> rooms =  roomRepository.findAll();
        return rooms.stream().map(this::mapToDto).toList();
    }

    private RoomDto mapToDto(Room room){
        return RoomDto.builder()
                .roomNumber(room.getRoomNumber())
                .description(room.getDescription())
                .capacity(room.getCapacity())
                .status(room.getStatus())
                .viewType(room.getViewType())
                .roomImage(room.getRoomImage())
                .build();
    }


}
