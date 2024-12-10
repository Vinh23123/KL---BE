package KL.KL_Booking_App.payload.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Builder
public class RoomImageDto {
    private Long roomImageId;

    private String assetId;

    private String secureUrl;

    private String publicId;

    private String signature;

    private String format;

    private String assetFolder;

    public RoomImageDto() {
    }

    public RoomImageDto( String assetId, String secureUrl) {
        this.assetId = assetId;
        this.secureUrl = secureUrl;
    }

    public RoomImageDto(Long roomImageId, String assetId, String secureUrl, String publicId, String signature, String format, String assetFolder) {
        this.roomImageId = roomImageId;
        this.assetId = assetId;
        this.secureUrl = secureUrl;
        this.publicId = publicId;
        this.signature = signature;
        this.format = format;
        this.assetFolder = assetFolder;
    }

    public Long getRoomImageId() {
        return roomImageId;
    }

    public void setRoomImageId(Long roomImageId) {
        this.roomImageId = roomImageId;
    }

    public String getAssetFolder() {
        return assetFolder;
    }

    public void setAssetFolder(String assetFolder) {
        this.assetFolder = assetFolder;
    }

    public String getPublicId() {
        return publicId;
    }

    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getSecureUrl() {
        return secureUrl;
    }

    public void setSecureUrl(String secureUrl) {
        this.secureUrl = secureUrl;
    }
}

