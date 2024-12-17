package KL.KL_Booking_App.service.impl;

import KL.KL_Booking_App.entity.*;
import KL.KL_Booking_App.entity.reservationType.ReservationType;
import KL.KL_Booking_App.entity.roomType.RoomType;
import KL.KL_Booking_App.exeption.ResourceNotFoundException;
import KL.KL_Booking_App.payload.response.ReservationDto;
import KL.KL_Booking_App.repository.ReservationRepository;
import KL.KL_Booking_App.repository.ReservationRoomRepository;
import KL.KL_Booking_App.repository.RoomRepository;
import KL.KL_Booking_App.repository.UserRepository;
import KL.KL_Booking_App.service.IDiscountService;
import KL.KL_Booking_App.service.IReservationService;
import KL.KL_Booking_App.utils.DiscountUtils;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationServiceImpl implements IReservationService {

    private final IDiscountService discountService;

    private final ReservationRepository reservationRepository;

    private final ReservationRoomRepository reservationRoomRepository;

    private final RoomRepository roomRepository;

    private final UserRepository userRepository;

    public ReservationServiceImpl(IDiscountService discountService, ReservationRepository reservationRepository, ReservationRoomRepository reservationRoomRepository, RoomRepository roomRepository, UserRepository userRepository) {
        this.discountService = discountService;
        this.reservationRepository = reservationRepository;
        this.reservationRoomRepository = reservationRoomRepository;
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public ReservationDto createReservation(Long roomId, Long discountId, ReservationDto reservationDto){
        if (!isCheckInBeforeCheckOut(reservationDto)){
            throw new IllegalArgumentException("Check In Date should be less than check out date");
        }
        // get room dto
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new ResourceNotFoundException("Room", "Id", roomId));
        if (room.getStatus().equals(RoomType.UNAVAILABLE)) {
            throw new IllegalArgumentException("Room is already booked");
        }

        Reservation reservation = new Reservation();
        reservation.setReservationType(ReservationType.PENDING);

        // apply discount
        double total  = calculateTotalCost(reservationDto, room);
        Discount discount = discountService.getDiscountById(discountId);

        double discountPrice = (discount.getDiscountAmount() / 100);
        double finalTotal = total - (total * discountPrice);

        reservation.setCheckIn(reservationDto.getCheckIn());
        reservation.setCheckOut(reservationDto.getCheckOut());
        reservation.setTotalAmount(finalTotal);
        reservation.setDiscount(discount);

        ReservationRoom reservationRoom = new ReservationRoom();
        reservationRoom.setReservation(reservation);
        reservationRoom.setRoom(room);

        // retrieve current user id in security to add reservation
        // FAKE user : NOTE

        reservationRepository.save(reservation);
        reservationRoomRepository.save(reservationRoom);

        return mapToReservationDto(reservation);
    }

    @Override
    public Reservation getReservationById(Long reservationId) {
        return reservationRepository.findById(reservationId).orElseThrow(()-> new ResourceNotFoundException("Reservation", "Id", reservationId));
    }

    @Override
    public void deleteReservation(Long reservationId) {
        Reservation reservation = getReservationById(reservationId);
        reservationRepository.delete(reservation);
    }

    @Override
    public List<ReservationDto> getAllReservations() {

        // retrieve current id user -> get all reservations
        // Fake user id 1
        long userId = 1;
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

        List<Reservation> reservations = reservationRepository.findByUserUserId(user.getUserId());
        return reservations.stream().map(this::mapToReservationDto).collect(Collectors.toList());
    }

    @Override
    public ReservationDto updateReservation(Long roomId ,Long discountId, ReservationDto reservationDto) {
        if (!isCheckInBeforeCheckOut(reservationDto)){
            throw new IllegalArgumentException("Check In Date should be less than check out date");
        }
        // get room dto
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new ResourceNotFoundException("Room", "Id", roomId));
        if (room.getStatus().equals(RoomType.UNAVAILABLE)) {
            throw new IllegalArgumentException("Room is already booked");
        }
        Reservation reservation = getReservationById(reservationDto.getReservationId());

        // apply discount
        double total  = calculateTotalCost(reservationDto, room);
        Discount discount = discountService.getDiscountById(discountId);

        double discountPrice = (discount.getDiscountAmount() / 100);
        double finalTotal = total - (total * discountPrice);

        reservation.setCheckIn(reservationDto.getCheckIn());
        reservation.setCheckOut(reservationDto.getCheckOut());
        reservation.setTotalAmount(finalTotal);
        reservation.setDiscount(discount);

        // retrieve current user id in security to add reservation
        // FAKE user : NOTE
        reservationRepository.save(reservation);

        return mapToReservationDto(reservation);
    }

    private boolean isCheckInBeforeCheckOut(ReservationDto reservationDto) {
        return reservationDto.getCheckIn().isBefore(reservationDto.getCheckOut());
    }

    private double calculateTotalCost(ReservationDto reservationDto, Room room) {
        // Ensure check-in and check-out are treated as dates only (ignoring time)
        LocalDate checkInDate = reservationDto.getCheckIn().toLocalDate();
        LocalDate checkOutDate = reservationDto.getCheckOut().toLocalDate();

        // Calculate the number of nights
        long nights = ChronoUnit.DAYS.between(checkInDate, checkOutDate);

        // Ensure that at least one night is booked if check-in and check-out are on the same day
        if (nights < 1) {
            nights = 1;
        }

        // Calculate the total cost
        return nights * room.getPricePerNight();
    }

    private ReservationDto mapToReservationDto(Reservation reservation){
        return ReservationDto
                .builder()
                .checkIn(reservation.getCheckIn())
                .checkOut(reservation.getCheckOut())
                .totalAmount(reservation.getTotalAmount())
                .build();
    }
}

// create reservation -> call api for reservation -> if payment method is credit card -> call api for payment -> UI redirect to url in call api -> payment -> depend on response ->  redirect user

