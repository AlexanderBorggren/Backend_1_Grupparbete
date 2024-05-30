package com.example.grupparbete_backend_1.services.impl;

import com.example.grupparbete_backend_1.dto.DetailedRoomTypeDto;
import com.example.grupparbete_backend_1.dto.RoomDto;
import com.example.grupparbete_backend_1.models.Room;
import com.example.grupparbete_backend_1.models.RoomType;
import com.example.grupparbete_backend_1.repositories.RoomRepo;
import com.example.grupparbete_backend_1.repositories.RoomTypeRepo;
import com.example.grupparbete_backend_1.services.RoomService;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepo roomRepo;
    private final RoomTypeRepo roomTypeRepo;
    public RoomServiceImpl(RoomRepo roomRepo, RoomTypeRepo roomTypeRepo){
        this.roomRepo = roomRepo;
        this.roomTypeRepo = roomTypeRepo;
    }

    @Override
    public RoomDto roomToRoomDto(Room room) {
        return RoomDto.builder().id(room.getId()).roomType(new DetailedRoomTypeDto(room.getRoomType().getId(),room.getRoomType().getRoomSize(), room.getRoomType().getMaxExtraBeds())).build();
    }

    @Override
    public Room roomDtoToRoom(RoomDto room, RoomType roomType) {
        return Room.builder().id(room.getId()).roomType(roomType).build();
    }
    @Override
    public String addRoom(RoomDto room) {
        RoomType roomType = roomTypeRepo.findById(room.getRoomType().getId()).get();
        roomRepo.save(roomDtoToRoom(room, roomType));
        return "Room has been saved";
    }
    @Override
    public List<RoomDto> getAllRoom() {
        return roomRepo.findAll().stream().map(k -> roomToRoomDto(k)).toList();
    }

    @Override
    public RoomDto findById(Long id) {
        Room r = roomRepo.findById(id).stream().findFirst().orElse(null);
        if(r == null){
            return null;
        }
        return roomToRoomDto(r);
    };
}

