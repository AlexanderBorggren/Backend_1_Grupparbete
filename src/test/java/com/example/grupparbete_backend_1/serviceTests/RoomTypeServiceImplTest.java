package com.example.grupparbete_backend_1.serviceTests;

import com.example.grupparbete_backend_1.dto.DetailedRoomTypeDto;
import com.example.grupparbete_backend_1.models.RoomType;
import com.example.grupparbete_backend_1.repositories.RoomTypeRepo;
import com.example.grupparbete_backend_1.services.impl.RoomTypeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@SpringBootTest
public class RoomTypeServiceImplTest {

    @Mock
    private RoomTypeRepo roomTypeRepo;
    @Mock
    private RoomTypeServiceImpl roomTypeService;
    private long roomTypeId = 1L;
    private String roomSize = "Single";
    private int maxExtraBeds = 0;
    private Double price = 300.0;

    RoomType roomType = new RoomType(roomTypeId, roomSize, maxExtraBeds, price,Timestamp.from(Instant.now()),Timestamp.from(Instant.now()));

    DetailedRoomTypeDto detailedRoomTypeDto = DetailedRoomTypeDto.builder().id(roomTypeId).roomSize(roomSize).maxExtraBeds(maxExtraBeds).build();

    @BeforeEach
    void setUp() {
        roomTypeService = new RoomTypeServiceImpl(roomTypeRepo);
    }

    @Test
    void getAllRoomTypes() {
        when(roomTypeRepo.findAll()).thenReturn(Arrays.asList(roomType));
        List<DetailedRoomTypeDto> allCustomers = roomTypeService.getAllRoomType();
        assertEquals(1, allCustomers.size());
    }


    @Test
    void addRoomType() {
        roomTypeService.addRoomType(detailedRoomTypeDto);
        verify(roomTypeRepo, times(1)).save(any());
    }

    @Test
    void roomTypeToDetailedRoomTypeDto() {
        DetailedRoomTypeDto actual = roomTypeService.roomTypeToRoomTypeDto(roomType);

        assertEquals(actual.getId(),roomType.getId());
        assertEquals(actual.getRoomSize(),roomType.getRoomSize());
        assertEquals(actual.getMaxExtraBeds(),roomType.getMaxExtraBeds());
    }

    @Test
    void detailedRoomTypeDtoToRoomTypeDto() {
        RoomType actual = roomTypeService.roomTypeDtoToRoomType(detailedRoomTypeDto);

        assertEquals(actual.getId(), detailedRoomTypeDto.getId());
        assertEquals(actual.getRoomSize(), detailedRoomTypeDto.getRoomSize());
        assertEquals(actual.getMaxExtraBeds(), detailedRoomTypeDto.getMaxExtraBeds());

    }

}
