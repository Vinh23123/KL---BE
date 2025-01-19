package KL.KL_Booking_App.utils;

import KL.KL_Booking_App.entity.ReservationRoom;
import KL.KL_Booking_App.entity.Room;
import KL.KL_Booking_App.payload.response.RoomDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RoomUtils {

    private final RoomImageUtils roomImageUtils;
    private final ReservationUtils reservationUtils;

    public RoomUtils(RoomImageUtils roomImageUtils, ReservationUtils reservationUtils) {
        this.roomImageUtils = roomImageUtils;
        this.reservationUtils = reservationUtils;
    }


    public Room mapToRoomEntity(RoomDto roomDto){
        return Room.builder()
                .roomId(roomDto.getRoomId())
                .roomNumber(roomDto.getRoomNumber())
                .description(roomDto.getDescription())
                .capacity(roomDto.getCapacity())
                .status(roomDto.getStatus())
                .viewType(roomDto.getViewType())
                .reviews(roomDto.getReviews())
                .pricePerNight(roomDto.getPricePerNight())
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
                .pricePerNight(room.getPricePerNight())
                .reviews(room.getReviews())
                .roomImageDtos(room.getRoomImage().stream().map(roomImageUtils::mapRoomImageToDto).toList())
                .hotel(room.getHotel())
                .build();
    }

    public RoomDto mapToRoomDb(Room room){
        List<ReservationRoom> reservationRoom = room.getReservationRoom();
        return RoomDto.builder()
                .roomId(room.getRoomId())
                .roomNumber(room.getRoomNumber())
                .description(room.getDescription())
                .capacity(room.getCapacity())
                .status(room.getStatus())
                .viewType(room.getViewType())
                .pricePerNight(room.getPricePerNight())
                .reviews(room.getReviews())
                .roomImageDtos(room.getRoomImage().stream().map(roomImageUtils::mapRoomImageToDto).toList())
                .reservationList(reservationRoom.stream().map(res -> reservationUtils.mapToReservationDto(res.getReservation())).collect(Collectors.toList()))
                .hotel(room.getHotel())
                .build();
    }
}
