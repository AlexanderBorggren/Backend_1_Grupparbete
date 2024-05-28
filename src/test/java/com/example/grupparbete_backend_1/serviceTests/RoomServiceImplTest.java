package com.example.grupparbete_backend_1.serviceTests;

import com.example.grupparbete_backend_1.dto.DetailedRoomTypeDto;
import com.example.grupparbete_backend_1.dto.RoomDto;
import com.example.grupparbete_backend_1.models.Room;
import com.example.grupparbete_backend_1.models.RoomType;
import com.example.grupparbete_backend_1.repositories.RoomRepo;
import com.example.grupparbete_backend_1.repositories.RoomTypeRepo;
import com.example.grupparbete_backend_1.services.impl.BookingServiceImpl;
import com.example.grupparbete_backend_1.services.impl.RoomServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class RoomServiceImplTest {

    @Mock
    private RoomTypeRepo roomTypeRepo;
    @Mock
    private RoomRepo roomRepo;
    @Mock
    private RoomServiceImpl roomService;
    private long roomTypeId = 1L;
    private String roomSize = "Single";
    private int maxExtraBeds = 0;
    private long roomId = 1L;
    private Double price = 300.0;


    RoomType roomType = new RoomType(roomTypeId, roomSize, maxExtraBeds, price, Timestamp.from(Instant.now()),Timestamp.from(Instant.now()));
    DetailedRoomTypeDto roomTypeDto = DetailedRoomTypeDto.builder().id(roomTypeId).roomSize(roomSize).maxExtraBeds(maxExtraBeds).build();

    Room room = new Room(roomId,roomType, Timestamp.from(Instant.now()),Timestamp.from(Instant.now()));
    RoomDto roomDto = RoomDto.builder().id(roomId).roomType(roomTypeDto).build();

    @BeforeEach
    void setUp() {
        roomService = new RoomServiceImpl(roomRepo,roomTypeRepo);

    }
    @Test
    void getAllRooms() {
        when(roomRepo.findAll()).thenReturn(Arrays.asList(room));
        List<RoomDto> allCustomers = roomService.getAllRoom();
        assertEquals(1, allCustomers.size());
    }


    @Test
    void addRoom() {
        when(roomTypeRepo.findById(anyLong())).thenReturn(Optional.of(new RoomType()));
        String result = roomService.addRoom(roomDto);
        verify(roomRepo, times(1)).save(any(Room.class));

        assertEquals("Room har sparats", result);
    }

    @Test
    void roomToRoomDto() {
        RoomDto actual = roomService.roomToRoomDto(room);
        assertEquals(actual.getId(), room.getId());
        assertEquals(actual.getRoomType().getId(), room.getRoomType().getId());
    }
    @Test
    void roomDtoToRoom() {
        Room actual = roomService.roomDtoToRoom(roomDto, roomType);
        assertEquals(actual.getId(), roomDto.getId());
        assertEquals(actual.getRoomType().getId(), roomDto.getRoomType().getId());
    }
}
