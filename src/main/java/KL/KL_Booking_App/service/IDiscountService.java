package KL.KL_Booking_App.service;

import KL.KL_Booking_App.entity.Discount;
import KL.KL_Booking_App.payload.response.DiscountDto;

import java.util.List;

public interface IDiscountService {
    Discount getDiscountById(Long discountId);
    List<DiscountDto> getAllDiscounts();
}
