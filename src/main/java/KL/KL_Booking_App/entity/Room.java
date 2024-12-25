package KL.KL_Booking_App.entity;

import KL.KL_Booking_App.entity.roomType.RoomType;
import KL.KL_Booking_App.entity.roomType.ViewType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.NumberFormat;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "Room")
@Builder
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;

    @Column(name = "roomNumber")
    private int roomNumber;

    @Lob
    private String description;

    private int capacity;

    private double price;

    @Enumerated(EnumType.STRING)
    private RoomType status;

    @Column(name = "viewType")
    @Enumerated(EnumType.STRING)
    private ViewType viewType;

    private double pricePerNight;

    @CreationTimestamp
    @Column(name = "createdAt",updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updatedAt",insertable = false)
    private Timestamp updatedAt;

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    @JsonIgnore
    private Hotel hotel;

    // orphanRemoval = true -> delete orphaned entities from the database.
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    @Column(name = "roomImage")
    private List<RoomImage> roomImage;

    @OneToMany(mappedBy = "room")
    @JsonIgnore
    private List<ReservationRoom> reservationRoom;

    @OneToMany(mappedBy = "room")
    private List<Review> reviews;

    public Room() {
    }

    public Room(Long roomId, int roomNumber, String description, int capacity, double price, RoomType status, ViewType viewType, double pricePerNight, Timestamp createdAt, Timestamp updatedAt, Hotel hotel, List<RoomImage> roomImage, List<ReservationRoom> reservationRoom, List<Review> reviews) {
        this.roomId = roomId;
        this.roomNumber = roomNumber;
        this.description = description;
        this.capacity = capacity;
        this.price = price;
        this.status = status;
        this.viewType = viewType;
        this.pricePerNight = pricePerNight;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.hotel = hotel;
        this.roomImage = roomImage;
        this.reservationRoom = reservationRoom;
        this.reviews = reviews;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(double pricePerNight) {
        this.pricePerNight = pricePerNight;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public RoomType getStatus() {
        return status;
    }

    public void setStatus(RoomType status) {
        this.status = status;
    }

    public ViewType getViewType() {
        return viewType;
    }

    public void setViewType(ViewType viewType) {
        this.viewType = viewType;
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

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public List<RoomImage> getRoomImage() {
        return roomImage;
    }

    public void setRoomImage(List<RoomImage> roomImage) {
        this.roomImage = roomImage;
    }

    public List<ReservationRoom> getReservationRoom() {
        return reservationRoom;
    }

    public void setReservationRoom(List<ReservationRoom> reservationRoom) {
        this.reservationRoom = reservationRoom;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
}
