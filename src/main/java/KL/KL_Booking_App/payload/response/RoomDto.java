package KL.KL_Booking_App.payload.response;

import KL.KL_Booking_App.entity.Hotel;
import KL.KL_Booking_App.entity.ReservationRoom;
import KL.KL_Booking_App.entity.Review;
import KL.KL_Booking_App.entity.roomType.RoomType;
import KL.KL_Booking_App.entity.roomType.ViewType;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.util.List;

@Builder
public class RoomDto {
    // ou could use @JsonInclude in a getter so that the attribute would be shown if the value is not null.
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long roomId;

    @NotNull(message = "Room Number must not be blank.")
    private int roomNumber;

    @Size(max = 1200, message = "The description can not be larger than 1200 characters")
    private String description;

    @NotNull(message = "Capacity must not be blank.")
    private int capacity;

    @NotNull(message = "Price must not be blank.")
    private double price;


    @NotNull(message = "Status must not be blank.")
    private RoomType status;

    @NotNull(message = "ViewType must not be blank.")
    private ViewType viewType;

    @NotNull(message = "Price Per Night must not be blank.")
    private double pricePerNight;

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

    public RoomDto(Long roomId, int roomNumber, String description, int capacity, double price, RoomType status, ViewType viewType, double pricePerNight, Hotel hotel, List<RoomImageDto> roomImageDtos, List<ReservationRoom> reservationRoom, List<Review> reviews) {
        this.roomId = roomId;
        this.roomNumber = roomNumber;
        this.description = description;
        this.capacity = capacity;
        this.price = price;
        this.status = status;
        this.viewType = viewType;
        this.pricePerNight = pricePerNight;
        this.hotel = hotel;
        this.roomImageDtos = roomImageDtos;
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
