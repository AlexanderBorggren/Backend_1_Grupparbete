package com.example.grupparbete_backend_1.Events;

import com.example.grupparbete_backend_1.models.Room;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.validation.annotation.Validated;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = RoomClosed.class,name="RoomClosed"),
        @JsonSubTypes.Type(value = RoomCleaningFinished.class,name="RoomCleaningFinished"),
        @JsonSubTypes.Type(value = RoomCleaningStarted.class,name="RoomCleaningStarted"),
        @JsonSubTypes.Type(value = RoomOpened.class,name="RoomOpened")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Validated
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class EventBase {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;
    private String message;
    @ManyToOne
    @JoinColumn
    private Room RoomNo;
    private LocalDateTime TimeStamp;
    @UpdateTimestamp
    private Timestamp updatedate;

    public String getUser() {
        return "No user specified";
    }
    public void setMessage() {
        this.message = "";
    }
}

