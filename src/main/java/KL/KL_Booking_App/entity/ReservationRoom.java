package KL.KL_Booking_App.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "ReservationRoom")
public class ReservationRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reservationRoomId;

    @ManyToOne
    @JoinColumn(name = "reservation_id", referencedColumnName = "reservationId")
    private Reservation reservation;

    @ManyToOne
    @JoinColumn(name = "room_id", referencedColumnName = "roomId")
    private Room room;

    public ReservationRoom() {
    }

    public ReservationRoom(int reservationRoomId, Reservation reservation, Room room) {
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

    public int getReservationRoomId() {
        return reservationRoomId;
    }

    public void setReservationRoomId(int reservationRoomId) {
        this.reservationRoomId = reservationRoomId;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
