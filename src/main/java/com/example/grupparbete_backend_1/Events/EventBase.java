package com.example.grupparbete_backend_1.Events;

import com.example.grupparbete_backend_1.models.RoomType;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

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
public class EventBase {
    //Behövs nog inte?
    //private String eventType;
    private String message;

    public LocalDateTime TimeStamp;

}

