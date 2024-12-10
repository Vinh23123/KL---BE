package KL.KL_Booking_App.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name = "RoomImage")
@Builder
public class RoomImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roomImageId")
    private Long roomImageId;

    @Column(name = "assetId")
    private String assetId;

    private String publicId;

    private String signature;

    private String format;

    private String assetFolder;

    @Column(name = "secureUrl")
    private String secureUrl;

    @CreationTimestamp
    @Column(name = "createdAt",updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updatedAt",insertable = false)
    private Timestamp updatedAt;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    @JsonIgnore

//    @JsonBackReference
    private Room room;

    public RoomImage(Long roomImageId, String assetId, String publicId, String signature, String format, String assetFolder, String secureUrl, Timestamp createdAt, Timestamp updatedAt, Room room) {
        this.roomImageId = roomImageId;
        this.assetId = assetId;
        this.publicId = publicId;
        this.signature = signature;
        this.format = format;
        this.assetFolder = assetFolder;
        this.secureUrl = secureUrl;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.room = room;
    }

    public String getAssetFolder() {
        return assetFolder;
    }

    public void setAssetFolder(String assetFolder) {
        this.assetFolder = assetFolder;
    }

    public String getPublicId() {
        return publicId;
    }

    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public RoomImage() {
    }

    public Long getRoomImageId() {
        return roomImageId;
    }

    public void setRoomImageId(Long roomImageId) {
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
