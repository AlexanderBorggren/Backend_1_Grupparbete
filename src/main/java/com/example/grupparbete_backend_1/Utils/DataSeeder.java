/*package com.example.grupparbete_backend_1.Utils;

import com.example.grupparbete_backend_1.dto.ShippersCatalogDto;
import com.example.grupparbete_backend_1.models.Shippers;
import com.example.grupparbete_backend_1.models.ShippersCatalog;
import com.example.grupparbete_backend_1.repositories.ShippersRepo;
import com.example.grupparbete_backend_1.services.ShippersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder {
    @Autowired
    private ShippersService shippersService;

    ShippersCatalogDto shippersCatalog = new ShippersCatalog();


    public void seedData() {

            if(!shippersCatalog.shippersList.isEmpty()){
                return;
            }
            for(int i =0; i < shippersCatalog.shippersList.size(); i++) {
                shippersService.addShippers(shippersCatalog.shippersList.get(i));
            }
        }
    }
}
*/