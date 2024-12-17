package KL.KL_Booking_App.utils;

import KL.KL_Booking_App.entity.Discount;
import KL.KL_Booking_App.payload.response.DiscountDto;
import org.springframework.stereotype.Component;

@Component
public class DiscountUtils {
    public DiscountDto convertDiscountToDto(Discount discount){
        return DiscountDto.builder()
                .discountId(discount.getDiscountId())
                .discountAmount(discount.getDiscountAmount())
                .expirationDate(discount.getExpirationDate())
                .build();
    }

    public Discount convertDiscountDtoToEntity(DiscountDto discountDto){
        return Discount.builder()
                .discountId(discountDto.getDiscountId())
                .discountAmount(discountDto.getDiscountAmount())
                .expirationDate(discountDto.getExpirationDate())
                .build();
    }
}
