package KL.KL_Booking_App.payload.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Builder
public class RoomImageDto {

    private String assetId;

    private String secureUrl;

    public RoomImageDto() {
    }

    public RoomImageDto( String assetId, String secureUrl) {
        this.assetId = assetId;
        this.secureUrl = secureUrl;
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

