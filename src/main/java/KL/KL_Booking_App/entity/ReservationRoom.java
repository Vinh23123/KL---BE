package KL.KL_Booking_App.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Builder;

import java.util.List;

@Entity
@Table(name = "ReservationRoom")
@Builder
public class ReservationRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationRoomId;

    @ManyToOne
    @JoinColumn(name = "reservation_id", referencedColumnName = "reservationId")
    @JsonIgnore
    private Reservation reservation;

    @ManyToOne
    @JoinColumn(name = "room_id", referencedColumnName = "roomId", nullable = false)
    private Room room;

    public ReservationRoom() {
    }

    public ReservationRoom(Long reservationRoomId, Reservation reservation, Room room) {
        this.reservationRoomId = reservationRoomId;
        this.reservation = reservation;
        this.room = room;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public Long getReservationRoomId() {
        return reservationRoomId;
    }

    public void setReservationRoomId(Long reservationRoomId) {
        this.reservationRoomId = reservationRoomId;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
