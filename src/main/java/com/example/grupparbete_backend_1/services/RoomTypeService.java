package com.example.grupparbete_backend_1.services;

import com.example.grupparbete_backend_1.dto.DetailedRoomTypeDto;
import com.example.grupparbete_backend_1.models.RoomType;
import java.util.List;

public interface RoomTypeService {

    public DetailedRoomTypeDto roomTypeToRoomTypeDto(RoomType roomType);
    public RoomType roomTypeDtoToRoomType(DetailedRoomTypeDto roomType);
    public List<DetailedRoomTypeDto> getAllRoomType();

    public String addRoomType(DetailedRoomTypeDto roomType);
    public DetailedRoomTypeDto findById(Long id);
}
