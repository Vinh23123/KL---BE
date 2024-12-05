package KL.KL_Booking_App.service.impl;

import KL.KL_Booking_App.entity.Hotel;
import KL.KL_Booking_App.entity.Room;
import KL.KL_Booking_App.entity.RoomImage;
import KL.KL_Booking_App.entity.roomType.RoomType;
import KL.KL_Booking_App.exeption.ResourceNotFoundException;
import KL.KL_Booking_App.payload.response.RoomDto;
import KL.KL_Booking_App.payload.response.RoomImageDto;
import KL.KL_Booking_App.repository.RoomRepository;
import KL.KL_Booking_App.service.IHotelService;
import KL.KL_Booking_App.service.IRoomService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomServiceImpl implements IRoomService {

    private final RoomRepository roomRepository;

    private final IHotelService hotelService;

    public RoomServiceImpl(RoomRepository roomRepository, IHotelService hotelService) {
        this.roomRepository = roomRepository;
        this.hotelService = hotelService;
    }

    // get room by room id according to hotel id
    @Override
    public RoomDto getRoomById(Long roomId) {
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new ResourceNotFoundException("Room", "Id", roomId));
        // return dto later
        return mapToDto(room);
    }

    @Override
    public List<RoomDto> getAllRoomsByHotelId(Long hotelId) {
        List<Room> rooms = roomRepository.findByHotelId(hotelId);
        return rooms.stream().map(this::mapToDto).toList();
    }

    @Override
    public RoomDto createANewRoom(Long hotelId, RoomDto roomDto) {

        Hotel hotel = hotelService.findHotelById(hotelId);
        Room room = Room
                .builder()
                .roomNumber(roomDto.getRoomNumber())
                .description(roomDto.getDescription())
                .capacity(roomDto.getCapacity())
                .status(RoomType.AVAILABLE)
                .viewType(roomDto.getViewType())
                .hotel(hotel)
                .build();
        // save a room
        roomRepository.save(room);

        return mapToDto(room);
    }

    @Override
    public RoomDto updateRoomById(RoomDto roomDto) {
        Room room = roomRepository.findById(roomDto.getRoomId()).orElseThrow(() -> new ResourceNotFoundException("Room", "Id", roomDto.getRoomId()));

        room.setRoomNumber(roomDto.getRoomNumber());
        room.setDescription(roomDto.getDescription());
        room.setCapacity(roomDto.getCapacity());
        room.setStatus(roomDto.getStatus());
        room.setViewType(roomDto.getViewType());
        // save a room
        roomRepository.save(room);

        return mapToDto(room);
    }

    private RoomDto mapToDto(Room room){
        return RoomDto.builder()
                .roomId(room.getRoomId())
                .roomNumber(room.getRoomNumber())
                .description(room.getDescription())
                .capacity(room.getCapacity())
                .status(room.getStatus())
                .viewType(room.getViewType())
                .build();
    }

    private RoomImageDto mapRoomImageToDto(RoomImage roomImage){
        return RoomImageDto.builder()
                .assetId(roomImage.getAssetId())
                .secureUrl(roomImage.getSecureUrl())
                .build();
    }

    private RoomImage mapRoomImageToEntity(RoomImageDto roomImageDto){
        return RoomImage.builder()
                .assetId(roomImageDto.getAssetId())
                .secureUrl(roomImageDto.getSecureUrl())
                .build();
    }

}
