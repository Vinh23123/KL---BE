package KL.KL_Booking_App.controller;

import KL.KL_Booking_App.constants.Global;
import KL.KL_Booking_App.payload.response.PaymentDto;
import KL.KL_Booking_App.payload.response.Response;
import KL.KL_Booking_App.service.IPaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.*;

@RestController
@RequestMapping("api/v1")
@CrossOrigin(origins = "http://localhost:5173")
public class PaymentController {

    private final IPaymentService paymentService;

    public PaymentController(IPaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("reservations/{reservationId}/payments/create")
    public ResponseEntity<Response> createPayment(@PathVariable(value = "reservationId") Long reservationId) throws UnsupportedEncodingException {
        PaymentDto paymentDto = paymentService.createPayment(reservationId);
        return new ResponseEntity<>(Response.builder()
                .status(Global.STATUS_SUCCESS)
                .message(Global.MESSAGE_GET_SUCCESSFULLY)
                // redirect to VNP -> return the sponse -> FE catch redirect to home page
                .data(paymentDto)
                .build(), HttpStatus.OK);
    }

    @PostMapping("payment/return")
    public ResponseEntity<Response> handlePaymentReturn(@RequestBody Map<String, String> vnpayParams) {
        PaymentDto paymentDto = paymentService.handlePaymentReturn(vnpayParams);
        return new ResponseEntity<>(Response.builder()
                .status(Global.STATUS_SUCCESS)
                .message(Global.MESSAGE_GET_SUCCESSFULLY)
                .data(paymentDto)
                .build(), HttpStatus.OK);
    }
}
