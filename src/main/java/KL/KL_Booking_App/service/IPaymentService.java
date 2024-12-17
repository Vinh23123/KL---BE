package KL.KL_Booking_App.service;

import KL.KL_Booking_App.payload.response.PaymentDto;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public interface IPaymentService {
    PaymentDto createPayment(Long reservationId) throws UnsupportedEncodingException;
    PaymentDto handlePaymentReturn(Long reservationId,  Long paymentId,Map<String, String> vnpayParams);

}
