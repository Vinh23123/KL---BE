package KL.KL_Booking_App.payload.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Builder
public class RoomImageDto {

    private String assetId;

    private String secureUrl;

    private String publicId;

    private String signature;

    private String format;

    public RoomImageDto() {
    }

    public RoomImageDto( String assetId, String secureUrl) {
        this.assetId = assetId;
        this.secureUrl = secureUrl;
    }

    public RoomImageDto(String assetId, String secureUrl, String publicId, String signature, String format) {
        this.assetId = assetId;
        this.secureUrl = secureUrl;
        this.publicId = publicId;
        this.signature = signature;
        this.format = format;
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

