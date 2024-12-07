package KL.KL_Booking_App.service;

import KL.KL_Booking_App.entity.Room;
import KL.KL_Booking_App.payload.response.RoomDto;

import java.util.List;

public interface IRoomService {
    RoomDto getRoomById(Long roomId);

    List<RoomDto> getAllRoomsByHotelId(Long hotelId);

    RoomDto createANewRoom(Long hotelId,RoomDto roomDto);

    RoomDto updateRoomById(RoomDto roomDto);

    void deleteRoomById(Long roomId);
}
