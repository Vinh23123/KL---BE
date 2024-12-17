package KL.KL_Booking_App.controller;

import KL.KL_Booking_App.constants.Global;
import KL.KL_Booking_App.payload.response.DiscountDto;
import KL.KL_Booking_App.payload.response.Response;
import KL.KL_Booking_App.service.IDiscountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/v1")
public class DiscountController {

    private final IDiscountService discountService;

    public DiscountController(IDiscountService discountService) {
        this.discountService = discountService;
    }

    @GetMapping("/discounts")
    public ResponseEntity<Response> fetchAllRoomsByHotelId(@PathVariable(value = "hotelId") Long hotelId){
        List<DiscountDto> discountsDto = discountService.getAllDiscounts();
        return new ResponseEntity<>(
                Response.builder()
                        .status(Global.STATUS_SUCCESS)
                        .data(discountsDto)
                        .message(Global.MESSAGE_GET_SUCCESSFULLY)
                        .time(new SimpleDateFormat("dd-MM-yyyy").format(new Date()))
                        .build()
                , HttpStatus.OK);
    }
}
