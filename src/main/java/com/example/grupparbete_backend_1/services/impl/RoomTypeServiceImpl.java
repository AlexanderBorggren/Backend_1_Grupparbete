package com.example.grupparbete_backend_1.services.impl;

import com.example.grupparbete_backend_1.dto.DetailedRoomTypeDto;
import com.example.grupparbete_backend_1.models.RoomType;
import com.example.grupparbete_backend_1.repositories.RoomTypeRepo;
import com.example.grupparbete_backend_1.services.RoomTypeService;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomTypeServiceImpl implements RoomTypeService {

    private final RoomTypeRepo roomTypeRepo;
    public RoomTypeServiceImpl(RoomTypeRepo roomTypeRepo){
        this.roomTypeRepo = roomTypeRepo;
    }

    @Override
    public DetailedRoomTypeDto roomTypeToRoomTypeDto(RoomType roomType) {
        return DetailedRoomTypeDto.builder().id(roomType.getId()).roomSize(roomType.getRoomSize()).maxExtraBeds(roomType.getMaxExtraBeds()).build();
    }

    @Override
    public RoomType roomTypeDtoToRoomType(DetailedRoomTypeDto roomType) {
        return RoomType.builder().id(roomType.getId()).roomSize(roomType.getRoomSize()).maxExtraBeds(roomType.getMaxExtraBeds()).build();
    }
    @Override
    public String addRoomType(DetailedRoomTypeDto roomType) {
        roomTypeRepo.save(roomTypeDtoToRoomType(roomType));
        return "Roomtype har sparats";
    }

    @Override
    public DetailedRoomTypeDto findById(Long id) {
        RoomType c = roomTypeRepo.findById(id).stream().findFirst().orElse(null);
        if(c == null){
            return null;
        }
        return roomTypeToRoomTypeDto(c);
    }

    @Override
    public List<DetailedRoomTypeDto> getAllRoomType() {
        return roomTypeRepo.findAll().stream().map(k -> roomTypeToRoomTypeDto(k)).toList();
    }
}
