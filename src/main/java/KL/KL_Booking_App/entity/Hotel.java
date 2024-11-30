package KL.KL_Booking_App.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "Hotel")
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int hotelId;

    @Column(name = "hotel_name")
    private String hotelName;

    private String description;

    @CreationTimestamp
    @Column(name = "createdAt",updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updatedAt",insertable = false)
    private Timestamp updatedAt;

    @OneToMany(mappedBy = "hotel")
    private List<Room> rooms;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id", referencedColumnName = "locationId")
    private Location location;

    public Hotel() {
    }

    public Hotel(int hotelId, String hotelName, String description, Timestamp createdAt, Timestamp updatedAt, List<Room> rooms, Location location) {
        this.hotelId = hotelId;
        this.hotelName = hotelName;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.rooms = rooms;
        this.location = location;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

}
