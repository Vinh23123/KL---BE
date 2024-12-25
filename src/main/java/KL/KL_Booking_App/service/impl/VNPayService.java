package KL.KL_Booking_App.service.impl;

import KL.KL_Booking_App.config.VnpayConfig;
import KL.KL_Booking_App.constants.Global;
import KL.KL_Booking_App.entity.Payment;
import KL.KL_Booking_App.entity.Reservation;
import KL.KL_Booking_App.entity.ReservationRoom;
import KL.KL_Booking_App.entity.Room;
import KL.KL_Booking_App.entity.paymentType.PaymentMethod;
import KL.KL_Booking_App.entity.paymentType.PaymentType;
import KL.KL_Booking_App.entity.reservationType.ReservationType;
import KL.KL_Booking_App.entity.roomType.RoomType;
import KL.KL_Booking_App.exeption.ResourceNotFoundException;
import KL.KL_Booking_App.payload.response.PaymentDto;
import KL.KL_Booking_App.repository.PaymentRepository;
import KL.KL_Booking_App.repository.ReservationRepository;
import KL.KL_Booking_App.repository.RoomRepository;
import KL.KL_Booking_App.service.IPaymentService;
import KL.KL_Booking_App.service.IReservationService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class VNPayService implements IPaymentService {

    private final IReservationService reservationService;
    private final RoomRepository roomRepository;
    private final PaymentRepository paymentRepository;
    private final ReservationRepository reservationRepository;
    private final VnpayConfig vnpayConfig;

    public VNPayService(IReservationService reservationService, RoomRepository roomRepository, PaymentRepository paymentRepository, ReservationRepository reservationRepository, VnpayConfig vnpayConfig) {
        this.reservationService = reservationService;
        this.roomRepository = roomRepository;
        this.paymentRepository = paymentRepository;
        this.reservationRepository = reservationRepository;
        this.vnpayConfig = vnpayConfig;
    }


    @Transactional
    @Override
    public PaymentDto createPayment(Long reservationId) throws UnsupportedEncodingException {
        Reservation reservation = reservationService.getReservationById(reservationId);

        Payment payment = new Payment();
        payment.setPaymentMethod(PaymentMethod.CREDIT_CARD);
        payment.setPaymentStatus(PaymentType.PENDING);
        payment.setReservation(reservation);

        String vnp_TxnRef = VnpayConfig.getRandomNumber(8);
        String vnp_TmnCode = vnpayConfig.getTmnCode();
        // Ensure amount is multiplied by 100 as required by VNPAY
        long formattedAmount = Math.round(reservation.getTotalAmount() * 100);
        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", VnpayConfig.vnp_Version);
        vnp_Params.put("vnp_Command", VnpayConfig.vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(formattedAmount));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_BankCode", "NCB");
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
        payment.setVnpTransactionRef(vnp_TxnRef);
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_OrderType", "ACCOMMODATION");
        vnp_Params.put("vnp_ReturnUrl", VnpayConfig.vnp_ReturnUrl);
        // Passing 127.0.0.1 is acceptable for testing but might not work in production. Use the client IP address dynamically in a real application
        vnp_Params.put("vnp_IpAddr", "127.0.0.1");

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = VnpayConfig.hmacSHA512(vnpayConfig.getSecretKey(), hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = VnpayConfig.vnp_PayUrl + "?" + queryUrl;
        paymentRepository.save(payment);

        return PaymentDto
                .builder()
                .paymentId(payment.getPaymentId())
                .paymentMethod(payment.getPaymentMethod())
                .paymentStatus(payment.getPaymentStatus())
                .paymentUrl(paymentUrl)
                .build();
    }

    @Transactional
    @Override
    public PaymentDto handlePaymentReturn(Map<String, String> vnpayParams) {

        Payment payment = paymentRepository.findByVnpTransactionRef(vnpayParams.get("vnp_TxnRef"));
        String responseCode = vnpayParams.get("vnp_ResponseCode");
        if ("00".equals(responseCode)) {
            // Payment successful
            String transactionId = vnpayParams.get("vnp_TransactionNo");
            String orderInfo = vnpayParams.get("vnp_OrderInfo");
            String amount = vnpayParams.get("vnp_Amount");
//             Update your reservation and payment status
            payment.setTransactionId(transactionId);
            payment.setOrderInfo(orderInfo);
            payment.setAmount(amount);
            payment.setPaymentStatus(PaymentType.COMPLETE);

            Reservation reservation = payment.getReservation();
            reservation.setReservationType(ReservationType.CONFIRMED);
            reservationRepository.save(reservation);
            List<Room> rooms = payment.getReservation().getReservationRoom().stream().map(ReservationRoom::getRoom).toList();
            rooms.forEach(room -> {
                room.setStatus(RoomType.UNAVAILABLE);
                roomRepository.save(room);
            });
            paymentRepository.save(payment);
            return PaymentDto
                    .builder()
                    .paymentId(payment.getPaymentId())
                    .paymentMethod(payment.getPaymentMethod())
                    .amount(payment.getAmount())
                    .orderInfo(payment.getOrderInfo())
                    .paymentStatus(payment.getPaymentStatus())
                    .updatedAt(payment.getUpdatedAt())
                    .build();
        } else {
            payment.setPaymentStatus(PaymentType.FAILED);
            paymentRepository.save(payment);
            return PaymentDto
                    .builder()
                    .paymentId(payment.getPaymentId())
                    .paymentMethod(payment.getPaymentMethod())
                    .paymentStatus(payment.getPaymentStatus())
                    .build();
        }
    }
}
