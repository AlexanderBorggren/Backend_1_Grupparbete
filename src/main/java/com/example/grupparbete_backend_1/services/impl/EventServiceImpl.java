package com.example.grupparbete_backend_1.services.impl;

import com.example.grupparbete_backend_1.dto.ShippersDto;
import com.example.grupparbete_backend_1.models.Shippers;
import com.example.grupparbete_backend_1.repositories.EventRepo;
import com.example.grupparbete_backend_1.repositories.ShippersRepo;
import com.example.grupparbete_backend_1.services.EventService;
import com.example.grupparbete_backend_1.services.ShippersService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepo eventRepo;
    public EventServiceImpl(EventRepo eventRepo){
        this.eventRepo = eventRepo;
    }

    @Override
    public ShippersDto shippersToShippersDto(Shippers shippers) {
        return ShippersDto.builder()
                .id(shippers.getId())
                .external_Shippers_Id(shippers.getExternal_Shippers_Id())
                .companyName(shippers.getCompanyName())
                .phone(shippers.getPhone())
                .regdate(shippers.getRegdate())
                .updatedate(shippers.getUpdatedate())
                .build();
    }

    @Override
    public Shippers shippersDtoToShippers(ShippersDto shippersDto) {
        return Shippers.builder()
                .id(shippersDto.getId())
                .external_Shippers_Id(shippersDto.getExternal_Shippers_Id())
                .companyName(shippersDto.getCompanyName())
                .phone(shippersDto.getPhone())
                .regdate(shippersDto.getRegdate())
                .updatedate(shippersDto.getUpdatedate())
                .build();
    }
    @Override
    public List<ShippersDto> getAllShippers() {
        return shippersRepo.findAll().stream().map(this::shippersToShippersDto).toList();
    }

    @Override
    public void addShippers(ShippersDto shippers) {
        shippersRepo.save(shippersDtoToShippers(shippers));
    }
    @Override
    public Shippers getShippersByExternalId(Long externalShippersId){
        return shippersRepo.findByExternal_Shippers_Id(externalShippersId);
    }

}
