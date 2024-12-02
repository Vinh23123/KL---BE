package KL.KL_Booking_App.payload.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
}
