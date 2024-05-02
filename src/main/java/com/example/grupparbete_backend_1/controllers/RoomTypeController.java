package com.example.grupparbete_backend_1.controllers;

import com.example.grupparbete_backend_1.dto.DetailedRoomTypeDto;
import com.example.grupparbete_backend_1.services.RoomTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;


@RestController
@RequiredArgsConstructor
public class RoomTypeController {

    private final RoomTypeService roomTypeService;

    @PostMapping("addRoomType")
    public void addRoomType(@RequestBody DetailedRoomTypeDto roomType){
        roomTypeService.addRoomType(roomType);
    }
    @RequestMapping("RoomTypes")
    public List<DetailedRoomTypeDto> getAllRoomTypes(){
        return roomTypeService.getAllRoomType();
    }

}

