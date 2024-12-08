package KL.KL_Booking_App.utils;

import KL.KL_Booking_App.entity.Room;
import KL.KL_Booking_App.payload.response.RoomDto;
import org.springframework.stereotype.Component;

@Component
public class RoomUtils {

    private final RoomImageUtils roomImageUtils;

    public RoomUtils(RoomImageUtils roomImageUtils) {
        this.roomImageUtils = roomImageUtils;
    }


    public Room mapToRoomEntity(RoomDto roomDto){
        return Room.builder()
                .roomId(roomDto.getRoomId())
                .roomNumber(roomDto.getRoomNumber())
                .description(roomDto.getDescription())
                .capacity(roomDto.getCapacity())
                .status(roomDto.getStatus())
                .viewType(roomDto.getViewType())
                .build();
    }
    public RoomDto mapToRoomDto(Room room){
        return RoomDto.builder()
                .roomId(room.getRoomId())
                .roomNumber(room.getRoomNumber())
                .description(room.getDescription())
                .capacity(room.getCapacity())
                .status(room.getStatus())
                .viewType(room.getViewType())
                .roomImageDtos(room.getRoomImage().stream().map(roomImageUtils::mapRoomImageToDto).toList())
                .build();
    }
}
