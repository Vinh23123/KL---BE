package KL.KL_Booking_App.entity;

import KL.KL_Booking_App.entity.roomType.RoomType;
import KL.KL_Booking_App.entity.roomType.ViewType;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "Room")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;

    @Column(name = "roomNumber")
    private int roomNumber;

    private String description;

    private int capacity;

    private double price;

    @Enumerated(EnumType.STRING)
    private RoomType status;

    @Column(name = "viewType")
    @Enumerated(EnumType.STRING)
    private ViewType viewType;

    @CreationTimestamp
    @Column(name = "createdAt",updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updatedAt",insertable = false)
    private Timestamp updatedAt;

    @ManyToOne
    @JoinColumn(name = "hotel_id", nullable = false)
    @JsonManagedReference
    private Hotel hotel;

    @OneToMany(mappedBy = "room")
    @Column(name = "roomImage")
    @JsonManagedReference
    private List<RoomImage> roomImage;

    @OneToMany(mappedBy = "room")
    @JsonManagedReference
    private List<ReservationRoom> reservationRoom;

    @OneToMany(mappedBy = "room")
    @JsonManagedReference
    private List<Review> reviews;

    public Room() {
    }

    public Room(Long roomId,  List<ReservationRoom> reservationRoom, List<RoomImage> roomImage, Hotel hotel, Timestamp updatedAt, Timestamp createdAt, ViewType viewType, RoomType status, int capacity, String description, int roomNumber) {
        this.roomId = roomId;
        this.reservationRoom = reservationRoom;
        this.roomImage = roomImage;
        this.hotel = hotel;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
        this.viewType = viewType;
        this.status = status;
        this.capacity = capacity;
        this.description = description;
        this.roomNumber = roomNumber;
    }

    public  List<ReservationRoom> getReservationRoom() {
        return reservationRoom;
    }

    public void setReservationRoom( List<ReservationRoom> reservationRoom) {
        this.reservationRoom = reservationRoom;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public RoomType getStatus() {
        return status;
    }

    public void setStatus(RoomType status) {
        this.status = status;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<RoomImage> getRoomImage() {
        return roomImage;
    }

    public void setRoomImage(List<RoomImage> roomImage) {
        this.roomImage = roomImage;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public ViewType getViewType() {
        return viewType;
    }

    public void setViewType(ViewType viewType) {
        this.viewType = viewType;
    }
}
