package KL.KL_Booking_App.utils;

import KL.KL_Booking_App.entity.RoomImage;
import KL.KL_Booking_App.payload.response.RoomImageDto;
import org.springframework.stereotype.Component;

@Component
public class RoomImageUtils {
    public RoomImageDto mapRoomImageToDto(RoomImage roomImage){
        return RoomImageDto.builder()
                .assetId(roomImage.getAssetId())
                .publicId(roomImage.getPublicId())
                .signature(roomImage.getSignature())
                .format(roomImage.getFormat())
                .secureUrl(roomImage.getSecureUrl())
                .build();
    }


    public RoomImage mapRoomImageToEntity(RoomImageDto roomImageDto){
        return RoomImage.builder()
                .assetId(roomImageDto.getAssetId())
                .secureUrl(roomImageDto.getSecureUrl())
                .build();
    }
}
