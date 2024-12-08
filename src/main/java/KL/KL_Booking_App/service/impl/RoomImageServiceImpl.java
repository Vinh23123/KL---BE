package KL.KL_Booking_App.service.impl;

import KL.KL_Booking_App.entity.Room;
import KL.KL_Booking_App.entity.RoomImage;
import KL.KL_Booking_App.payload.response.RoomDto;
import KL.KL_Booking_App.payload.response.RoomImageDto;
import KL.KL_Booking_App.repository.RoomImageRepository;
import KL.KL_Booking_App.service.IRoomImageService;
import KL.KL_Booking_App.service.IRoomService;
import KL.KL_Booking_App.utils.RoomImageUtils;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class RoomImageServiceImpl implements IRoomImageService {

    private final RoomImageRepository roomImageRepository;

    private final IRoomService roomService;

    private final Cloudinary cloudinary;
    private final RoomImageUtils roomImageUtils;

    public RoomImageServiceImpl(RoomImageRepository roomImageRepository, IRoomService roomService, Cloudinary cloudinary, RoomImageUtils roomImageUtils) {
        this.roomImageRepository = roomImageRepository;
        this.roomService = roomService;
        this.cloudinary = cloudinary;
        this.roomImageUtils = roomImageUtils;
    }


    @Override
    public RoomImageDto uploadImage(MultipartFile file, Long roomId) throws IOException {

        RoomDto roomDto = roomService.getRoomById(roomId);

        Map<String, Object> uploadResult  = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("resource_type", "auto"));

        String assetId = (String) uploadResult.get("asset_id");
        String publicId = (String) uploadResult.get("public_id");
        String signature = (String) uploadResult.get("signature");
        String format = (String) uploadResult.get("format");
        String secureUrl  = (String)  uploadResult.get("secure_url");

        RoomImage roomImage = new RoomImage();
        roomImage.setAssetId(assetId);
        roomImage.setPublicId(publicId);
        roomImage.setSignature(signature);
        roomImage.setFormat(format);
        roomImage.setSecureUrl(secureUrl);
        roomImage.setRoom(mapToRoomEntity(roomDto));

        roomImageRepository.save(roomImage);

        return roomImageUtils.mapRoomImageToDto(roomImage);
    }

    @Override
    public List<RoomImageDto> getAllRoomImages() {
        return List.of();
    }

    @Override
    public RoomImageDto updateRoomImage(RoomImageDto roomImageDto) {
        return null;
    }

    @Override
    public RoomImageDto fetchRoomImage(Long roomImageId) {
        return null;
    }

    @Override
    public void deleteRoomImage(Long roomImageId) {

    }


    private Room mapToRoomEntity(RoomDto roomDto){
        return Room.builder()
                .roomId(roomDto.getRoomId())
                .roomNumber(roomDto.getRoomNumber())
                .description(roomDto.getDescription())
                .capacity(roomDto.getCapacity())
                .status(roomDto.getStatus())
                .viewType(roomDto.getViewType())
                .build();
    }
}
