package KL.KL_Booking_App.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name = "room_image")
public class RoomImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int roomImageId;
    private String assetId;
    private String secureUrl;
    @CreationTimestamp
    @Column(name = "createdAt",updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updatedAt",updatable = true)
    private Timestamp updatedAt;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    public RoomImage(int roomImageId, String assetId, String secureUrl, Timestamp createdAt, Timestamp updatedAt, Room room) {
        this.roomImageId = roomImageId;
        this.assetId = assetId;
        this.secureUrl = secureUrl;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.room = room;
    }

    public RoomImage() {
    }

    public int getRoomImageId() {
        return roomImageId;
    }

    public void setRoomImageId(int roomImageId) {
        this.roomImageId = roomImageId;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getSecureUrl() {
        return secureUrl;
    }

    public void setSecureUrl(String secureUrl) {
        this.secureUrl = secureUrl;
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

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
