package KL.KL_Booking_App.service.impl;

import KL.KL_Booking_App.entity.Discount;
import KL.KL_Booking_App.exeption.ResourceNotFoundException;
import KL.KL_Booking_App.payload.response.DiscountDto;
import KL.KL_Booking_App.repository.DiscountRepository;
import KL.KL_Booking_App.service.IDiscountService;
import KL.KL_Booking_App.utils.DiscountUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiscountServiceImpl implements IDiscountService {

    private final DiscountRepository discountRepository;
    private final DiscountUtils discountUtils;

    public DiscountServiceImpl(DiscountRepository discountRepository, DiscountUtils discountUtils) {
        this.discountRepository = discountRepository;
        this.discountUtils = discountUtils;
    }

    @Override
    public Discount getDiscountById(Long discountId) {
        return discountRepository.findById(discountId).orElseThrow(()-> new ResourceNotFoundException("Discount", "Id", discountId));
    }

    @Override
    public List<DiscountDto> getAllDiscounts() {
        List<Discount> discounts = discountRepository.findAll();
        return discounts.stream().map(discountUtils::convertDiscountToDto).collect(Collectors.toList());
    }
}
