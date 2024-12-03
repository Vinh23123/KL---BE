package KL.KL_Booking_App.payload.response;

import KL.KL_Booking_App.entity.Hotel;
import KL.KL_Booking_App.entity.ReservationRoom;
import KL.KL_Booking_App.entity.Review;
import KL.KL_Booking_App.entity.RoomImage;
import KL.KL_Booking_App.entity.roomType.RoomType;
import KL.KL_Booking_App.entity.roomType.ViewType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
public class RoomDto {
    // ou could use @JsonInclude in a getter so that the attribute would be shown if the value is not null.
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long roomId;

    private int roomNumber;

    private String description;

    private int capacity;

    private double price;

    private RoomType status;

    private ViewType viewType;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Hotel hotel;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<RoomImageDto> roomImageDtos;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ReservationRoom> reservationRoom;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Review> reviews;

    public RoomDto() {
    }

    public RoomDto(Long roomId, int roomNumber, String description, int capacity, double price, RoomType status, ViewType viewType, Hotel hotel, List<RoomImageDto> roomImageDtos, List<ReservationRoom> reservationRoom, List<Review> reviews) {
        this.roomId = roomId;
        this.roomNumber = roomNumber;
        this.description = description;
        this.capacity = capacity;
        this.price = price;
        this.status = status;
        this.viewType = viewType;
        this.hotel = hotel;
        this.roomImageDtos = roomImageDtos;
        this.reservationRoom = reservationRoom;
        this.reviews = reviews;
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

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public List<RoomImageDto> getRoomImageDtos() {
        return roomImageDtos;
    }

    public void setRoomImageDtos(List<RoomImageDto> roomImageDtos) {
        this.roomImageDtos = roomImageDtos;
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
