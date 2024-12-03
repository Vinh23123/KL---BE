package KL.KL_Booking_App.service.impl;

import KL.KL_Booking_App.entity.Hotel;
import KL.KL_Booking_App.entity.Room;
import KL.KL_Booking_App.entity.RoomImage;
import KL.KL_Booking_App.entity.roomType.RoomType;
import KL.KL_Booking_App.exeption.ResourceNotFoundException;
import KL.KL_Booking_App.payload.response.RoomDto;
import KL.KL_Booking_App.payload.response.RoomImageDto;
import KL.KL_Booking_App.repository.RoomImageRepository;
import KL.KL_Booking_App.repository.RoomRepository;
import KL.KL_Booking_App.service.IHotelService;
import KL.KL_Booking_App.service.IRoomService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomServiceImpl implements IRoomService {

    private final RoomRepository roomRepository;

    private final RoomImageRepository roomImageRepository;

    private final IHotelService hotelService;

    public RoomServiceImpl(RoomRepository roomRepository , RoomImageRepository roomImageRepository, IHotelService hotelService) {
        this.roomRepository = roomRepository;
        this.roomImageRepository = roomImageRepository;
        this.hotelService = hotelService;
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
    public RoomDto updateRoomById(Long hotelId, Long roomId, RoomDto roomDto) {

        Hotel hotel = hotelService.findHotelById(hotelId);
        List<Room> roomList = hotel.getRooms();
        Room matchRoom = roomList.stream()
                .filter((room) -> room.getRoomId().equals(roomId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Room", "Id", roomId));

        matchRoom.setRoomNumber(roomDto.getRoomNumber());
        matchRoom.setDescription(roomDto.getDescription());
        matchRoom.setCapacity(roomDto.getCapacity());
        matchRoom.setStatus(roomDto.getStatus());
        matchRoom.setViewType(roomDto.getViewType());
        // save a room
        roomRepository.save(matchRoom);

        return mapToDto(matchRoom);
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
