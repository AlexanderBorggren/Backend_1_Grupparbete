package com.example.grupparbete_backend_1.services.impl;

import com.example.grupparbete_backend_1.dto.CustomerDto;
import com.example.grupparbete_backend_1.dto.DetailedCustomerDto;
import com.example.grupparbete_backend_1.dto.ShippersDto;
import com.example.grupparbete_backend_1.models.Customer;
import com.example.grupparbete_backend_1.models.Shippers;
import com.example.grupparbete_backend_1.repositories.CustomerRepo;
import com.example.grupparbete_backend_1.repositories.ShippersRepo;
import com.example.grupparbete_backend_1.services.CustomerService;
import com.example.grupparbete_backend_1.services.ShippersService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.util.Collections;
import java.util.List;

@Repository
@Service
public class ShippersServiceImpl implements ShippersService {

    private final ShippersRepo shippersRepo;
    public ShippersServiceImpl(ShippersRepo shippersRepo){
        this.shippersRepo = shippersRepo;
    }
    //    public ShippersDto shippersToShippersDto(Shippers shippers);
    //    public Shippers shippersDtoToShippers(ShippersDto shippers);

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
