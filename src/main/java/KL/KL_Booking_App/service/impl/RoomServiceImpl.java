package KL.KL_Booking_App.service.impl;

import KL.KL_Booking_App.entity.Hotel;
import KL.KL_Booking_App.entity.Location;
import KL.KL_Booking_App.entity.Room;
import KL.KL_Booking_App.entity.RoomImage;
import KL.KL_Booking_App.entity.roomType.RoomType;
import KL.KL_Booking_App.exeption.ResourceNotFoundException;
import KL.KL_Booking_App.payload.response.RoomDto;
import KL.KL_Booking_App.payload.response.RoomImageDto;
import KL.KL_Booking_App.payload.response.RoomResponse;
import KL.KL_Booking_App.repository.LocationRepository;
import KL.KL_Booking_App.repository.RoomRepository;
import KL.KL_Booking_App.service.IHotelService;
import KL.KL_Booking_App.service.IReservationService;
import KL.KL_Booking_App.service.IRoomService;
import KL.KL_Booking_App.utils.RoomUtils;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl implements IRoomService {

    private final RoomRepository roomRepository;

    private final IHotelService hotelService;

    private final RoomUtils roomUtils;

    private final LocationRepository locationRepository;

    public RoomServiceImpl(RoomRepository roomRepository, IHotelService hotelService, RoomUtils roomUtils, LocationRepository locationRepository) {
        this.roomRepository = roomRepository;
        this.hotelService = hotelService;
        this.roomUtils = roomUtils;
        this.locationRepository = locationRepository;
    }

    // get room by room id according to hotel id
    @Override
    public RoomDto getRoomById(Long roomId) {
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new ResourceNotFoundException("Room", "Id", roomId));
        // return dto later
        Location location = locationRepository.findByHotelHotelId(room.getHotel().getHotelId());
        room.getHotel().setLocation(location);

        return roomUtils.mapToRoomDto(room);
    }

    @Override
    public RoomResponse getAllRoomsByHotelId(Long hotelId, int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort. by(sortBy).descending();

        // create Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Room> rooms = roomRepository.findByHotelHotelId(hotelId, pageable);
        List<Room> roomList = rooms.getContent();
        List<RoomDto> roomDtos = roomList.stream().map(roomUtils::mapToRoomDto).toList();

        return RoomResponse
                .builder()
                .content(roomDtos)
                .pageNo(rooms.getNumber())
                .pageSize(rooms.getSize())
                .totalElements(rooms.getTotalElements())
                .totalPages(rooms.getTotalPages())
                .last(rooms.isLast())
                .build();
    }

    @Override
    public List<RoomDto> getAllRooms() {
        List<Room> rooms =  roomRepository.findAll();
        return rooms.stream().map(roomUtils::mapToRoomDto).collect(Collectors.toList());
    }

    @Override
    public RoomDto createANewRoom(Long hotelId, RoomDto roomDto) {

        Hotel hotel = hotelService.findHotelById(hotelId);
        Room room = Room
                .builder()
                .roomNumber(roomDto.getRoomNumber())
                .description(roomDto.getDescription())
                .capacity(roomDto.getCapacity())
                .status(roomDto.getStatus())
                .pricePerNight(roomDto.getPricePerNight())
                .viewType(roomDto.getViewType())
                .hotel(hotel)
                .build();
        // save a room
        roomRepository.save(room);

        return mapToRoomDto(room);
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

        return roomUtils.mapToRoomDto(room);
    }

    @Transactional
    @Override
    public void deleteRoomById(Long roomId) {
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new ResourceNotFoundException("Room", "Id", roomId));
        roomRepository.delete(room);
    }

    private RoomDto mapToRoomDto(Room room){
        return RoomDto.builder()
                .roomId(room.getRoomId())
                .roomNumber(room.getRoomNumber())
                .description(room.getDescription())
                .capacity(room.getCapacity())
                .status(room.getStatus())
                .viewType(room.getViewType())
                .pricePerNight(room.getPricePerNight())
                .reviews(room.getReviews())
                .hotel(room.getHotel())
                .build();
    }

}
