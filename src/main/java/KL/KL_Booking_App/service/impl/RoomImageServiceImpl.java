package KL.KL_Booking_App.service.impl;

import KL.KL_Booking_App.entity.Room;
import KL.KL_Booking_App.entity.RoomImage;
import KL.KL_Booking_App.exeption.ResourceNotFoundException;
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
import java.util.stream.Collectors;

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
    public List<RoomImageDto> uploadImage(List<MultipartFile> files, Long roomId) throws IOException {
        // Assuming roomService fetches room by ID
        RoomDto roomDto = roomService.getRoomById(roomId);
        // check list images
        if (roomDto.getRoomImageDtos().size() > 5){
            throw new IllegalArgumentException("Room has already 5 images.");
        }


        if (roomDto.getRoomImageDtos().size() + files.size() > 5){
            throw new IllegalArgumentException("The total images has already " + roomDto.getRoomImageDtos().size() + " images. You remains " + calculateImage(roomDto) + " images.") ;
        }

        // Define a folder path for the images based on the room ID
        String fileName = convertRootUrl(roomDto, roomId);

        // Process files in parallel and return a list of RoomImage entities
        List<RoomImage> roomImageList = files.stream()
                .map(file -> uploadSingleFile(file, fileName, roomDto))
                .collect(Collectors.toList());

        // Batch save images (assumes roomImageRepository supports batch save)
        roomImageRepository.saveAll(roomImageList);

        // Map RoomImage entities to DTOs
        return roomImageList.stream()
                .map(roomImageUtils::mapRoomImageToDto)
                .collect(Collectors.toList());
    }

    private RoomImage uploadSingleFile(MultipartFile file, String fileName, RoomDto roomDto) {
        try {
            // Upload file to Cloudinary and get result
            Map<String, Object> uploadResult = cloudinary.uploader().upload(file.getBytes(),
                    ObjectUtils.asMap("resource_type", "auto", "folder", fileName));

            // Extract details from the upload result
            String assetId = (String) uploadResult.get("asset_id");
            String publicId = (String) uploadResult.get("public_id");
            String signature = (String) uploadResult.get("signature");
            String format = (String) uploadResult.get("format");
            String secureUrl = (String) uploadResult.get("secure_url");
            String assetFolder = (String) uploadResult.get("asset_folder");

            // Create RoomImage entity and set attributes
            RoomImage roomImage = new RoomImage();
            roomImage.setAssetId(assetId);
            roomImage.setPublicId(publicId);
            roomImage.setSignature(signature);
            roomImage.setFormat(format);
            roomImage.setSecureUrl(secureUrl);
            roomImage.setAssetFolder(assetFolder);
            roomImage.setRoom(mapToRoomEntity(roomDto)); // Mapping RoomDto to Room entity

            return roomImage;
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload image: " + file.getOriginalFilename(), e);
        }
    }

    private String convertRootUrl(RoomDto roomDto , Long roomId){
        return "KL/hotel/room-" + roomId;
    }
    private Integer calculateImage(RoomDto roomDto){
        return 5 - roomDto.getRoomImageDtos().size();
    }

    @Override
    public void deleteRoomImage(Long roomImageId) {
        RoomImage roomImage = roomImageRepository.findById(roomImageId).orElseThrow(()-> new ResourceNotFoundException("Image", "Id", roomImageId));
        roomImageRepository.delete(roomImage);
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
