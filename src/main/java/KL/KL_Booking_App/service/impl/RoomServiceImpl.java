package KL.KL_Booking_App.service.impl;

import KL.KL_Booking_App.entity.Room;
import KL.KL_Booking_App.entity.RoomImage;
import KL.KL_Booking_App.entity.roomType.RoomType;
import KL.KL_Booking_App.exeption.ResourceNotFoundException;
import KL.KL_Booking_App.payload.response.RoomDto;
import KL.KL_Booking_App.payload.response.RoomImageDto;
import KL.KL_Booking_App.repository.RoomRepository;
import KL.KL_Booking_App.service.IRoomImageService;
import KL.KL_Booking_App.service.IRoomService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomServiceImpl implements IRoomService {

    private final RoomRepository roomRepository;

    private final IRoomImageService roomImageService;

    public RoomServiceImpl(RoomRepository roomRepository, IRoomService roomImageService, IRoomImageService roomImageService1) {
        this.roomRepository = roomRepository;
        this.roomImageService = roomImageService1;
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
    public RoomDto createANewRoom(RoomDto roomDto) {
        // modify exception later
        // room number
        roomRepository.findById(roomDto.getRoomId()).orElseThrow(() -> new ResourceNotFoundException("Room", "Id", roomDto.getRoomId()));

        // if room image is larger than 5 -> throw exception
        if (roomDto.getRoomImageDtos().size() > 5 ){
            throw new RuntimeException("List Image is greater than 5 images");
        }

        // convert image to url and asset id

        Room room = Room.builder()
                .roomNumber(roomDto.getRoomNumber())
                .description(roomDto.getDescription())
                .capacity(roomDto.getCapacity())
                .status(RoomType.AVAILABLE)
                .viewType(roomDto.getViewType()).build();
        // save a room
        roomRepository.save(room);

        // save room image
        roomDto.getRoomImageDtos().stream().map(this::mapRoomImageToEntity)
                .forEach(roomImageService::saveRoomImage);


        return mapToDto(room);
    }

    @Override
    public RoomDto updateRoomById(Long roomId, Room room) {
        return null;
    }

    private RoomDto mapToDto(Room room){
        return RoomDto.builder()
                .roomNumber(room.getRoomNumber())
                .description(room.getDescription())
                .capacity(room.getCapacity())
                .status(room.getStatus())
                .viewType(room.getViewType())
                .roomImageDtos(
                        room.getRoomImage()
                        .stream()
                        .map(this::mapRoomImageToDto).toList()
                )
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
