package KL.KL_Booking_App.controller;

import KL.KL_Booking_App.constants.Global;
import KL.KL_Booking_App.payload.response.ReservationDto;
import KL.KL_Booking_App.payload.response.Response;
import KL.KL_Booking_App.service.IReservationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/v1")
public class ReservationController {

    private final IReservationService reservationService;

    public ReservationController(IReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/reservations/rooms/{roomId}/discounts/{discountId}")
    public ResponseEntity<Response> createReservation(@PathVariable(value = "roomId")Long roomId,
                                                      @PathVariable(value = "discountId")Long discountId,
                                                      @Valid @RequestBody ReservationDto reservationDto){
      try {
          ReservationDto savedReservation = reservationService.createReservation(roomId, discountId, reservationDto);
          return new ResponseEntity<>(
                  Response.builder()
                          .status(Global.STATUS_SUCCESS)
                          .data(savedReservation)
                          .message(Global.MESSAGE_CREATED_SUCCESSFULLY)
                          .time(new SimpleDateFormat("dd-MM-yyyy").format(new Date()))
                          .build(),
                  HttpStatus.OK);
      } catch (Exception e){
          return new ResponseEntity<>(
                  Response.builder()
                          .status(Global.STATUS_FAILED)
                          .message(e.getMessage())
                          .time(new SimpleDateFormat("dd-MM-yyyy").format(new Date()))
                          .build(),
                  HttpStatus.BAD_REQUEST
          );
      }
    }

    @DeleteMapping("/reservations/{reservationId}")
    public ResponseEntity<Response> deleteReservation(@PathVariable(value = "reservationId")Long reservationId){
        try {
            reservationService.deleteReservation(reservationId);
            return new ResponseEntity<>(
                    Response.builder()
                            .status(Global.STATUS_SUCCESS)
                            .message(Global.MESSAGE_DELETED_SUCCESSFULLY)
                            .time(new SimpleDateFormat("dd-MM-yyyy").format(new Date()))
                            .build(),
                    HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(
                    Response.builder()
                            .status(Global.STATUS_FAILED)
                            .message(e.getMessage())
                            .time(new SimpleDateFormat("dd-MM-yyyy").format(new Date()))
                            .build(),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @GetMapping("/reservations")
    public ResponseEntity<Response> fetchAllRoomsByHotelId(){
        List<ReservationDto> reservationDtos = reservationService.getAllReservations();
        return new ResponseEntity<>(
                Response.builder()
                        .status(Global.STATUS_SUCCESS)
                        .data(reservationDtos)
                        .message(Global.MESSAGE_GET_SUCCESSFULLY)
                        .time(new SimpleDateFormat("dd-MM-yyyy").format(new Date()))
                        .build()
                , HttpStatus.OK);
    }

    @PutMapping("/reservations/rooms/{roomId}/discounts/{discountId}")
    public ResponseEntity<Response> updateReservation(@PathVariable(value = "roomId")Long roomId,
                                                      @PathVariable(value = "discountId")Long discountId,
                                                      @Valid @RequestBody ReservationDto reservationDto){
        try {
            ReservationDto updatedReservation = reservationService.updateReservation(roomId, discountId, reservationDto);
            return new ResponseEntity<>(
                    Response.builder()
                            .status(Global.STATUS_SUCCESS)
                            .data(updatedReservation)
                            .message(Global.MESSAGE_UPDATED_SUCCESSFULLY)
                            .time(new SimpleDateFormat("dd-MM-yyyy").format(new Date()))
                            .build(),
                    HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(
                    Response.builder()
                            .status(Global.STATUS_FAILED)
                            .message(e.getMessage())
                            .time(new SimpleDateFormat("dd-MM-yyyy").format(new Date()))
                            .build(),
                    HttpStatus.BAD_REQUEST
            );
        }
    }
}
