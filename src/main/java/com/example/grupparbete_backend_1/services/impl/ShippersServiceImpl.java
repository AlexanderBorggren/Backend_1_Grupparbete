package com.example.grupparbete_backend_1.services.impl;

import com.example.grupparbete_backend_1.dto.ShippersDto;
import com.example.grupparbete_backend_1.models.Shippers;
import com.example.grupparbete_backend_1.repositories.ShippersRepo;
import com.example.grupparbete_backend_1.services.ShippersService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

@Service
public class ShippersServiceImpl implements ShippersService {

    public final ShippersRepo shippersRepo;
    public final JSonStreamProvider jsonStreamProvider;

    public ShippersServiceImpl(ShippersRepo shippersRepo, JSonStreamProvider jsonStreamProvider){
        this.shippersRepo = shippersRepo;
        this.jsonStreamProvider = jsonStreamProvider;
    }
    public void fetchShippers() throws IOException {

        try {
            InputStream stream = jsonStreamProvider.getDataStream();
            ObjectMapper objectMapper = new ObjectMapper();

            List<Shippers> theShippers = Arrays.asList(objectMapper.readValue(stream, Shippers[].class));

            for (Shippers s : theShippers) {
                System.out.println(s.getExternal_Shippers_Id());
                System.out.println(s.getCompanyName());

                ShippersDto shippersDto = shippersToShippersDto(s);

                Shippers existingShippers = getShippersByExternalId(s.getExternal_Shippers_Id());
                if (existingShippers == null) {
                    addShippers(shippersDto);
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
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
