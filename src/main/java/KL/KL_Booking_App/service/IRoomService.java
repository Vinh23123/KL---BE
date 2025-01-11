package KL.KL_Booking_App.service;


import KL.KL_Booking_App.payload.response.RoomDto;
import KL.KL_Booking_App.payload.response.RoomResponse;

import java.util.List;

public interface IRoomService {
    RoomDto getRoomById(Long roomId);

    RoomResponse getAllRoomsByHotelId(Long hotelId, int pageNo, int pageSize, String sortBy, String sortDir);

    List<RoomDto> getAllRooms();

    RoomDto createANewRoom(Long hotelId,RoomDto roomDto);

    RoomDto updateRoomById(RoomDto roomDto);

    void deleteRoomById(Long roomId);

    void updateStatus(Long roomId);
}
