package com.example.grupparbete_backend_1.services;

import com.example.grupparbete_backend_1.dto.RoomDto;
import com.example.grupparbete_backend_1.models.Room;
import com.example.grupparbete_backend_1.models.RoomType;
import java.util.List;

public interface RoomService {
    public RoomDto roomToRoomDto(Room room);
    public Room roomDtoToRoom(RoomDto room, RoomType roomType);
    public List<RoomDto> getAllRoom();
    public String addRoom(RoomDto room);
    RoomDto findById(Long id);
}
