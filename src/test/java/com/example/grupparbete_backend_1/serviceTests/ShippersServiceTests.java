package com.example.grupparbete_backend_1.serviceTests;

import com.example.grupparbete_backend_1.dto.DetailedContractCustomerDto;
import com.example.grupparbete_backend_1.dto.ShippersDto;
import com.example.grupparbete_backend_1.models.Shippers;
import com.example.grupparbete_backend_1.repositories.ShippersRepo;
import com.example.grupparbete_backend_1.services.ShippersService;
import com.example.grupparbete_backend_1.services.impl.JSonStreamProvider;
import com.example.grupparbete_backend_1.services.impl.ShippersServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ShippersServiceTests {

    private final ShippersRepo shippersRepo = mock(ShippersRepo.class);
    private final JSonStreamProvider jSonStreamProvider = mock(JSonStreamProvider.class);

    private ShippersServiceImpl sut;

    @BeforeEach
    public void setUp() {
        sut = new ShippersServiceImpl(shippersRepo, jSonStreamProvider);
    }

    @Test
    void fetchShippersShouldMapCorrectlyAndSave() throws IOException {

        //Arrange
        when(jSonStreamProvider.getDataStream()).thenReturn(getClass().getClassLoader().getResourceAsStream("shippers.json"));
        when(shippersRepo.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        //act
        sut.fetchShippers();

        // Assert
        verify(shippersRepo, times(3)).findByExternal_Shippers_Id(Mockito.anyLong());
        verify(jSonStreamProvider, times(1)).getDataStream();
        verify(shippersRepo, times(1)).save(argThat(shipper -> shipper.getExternal_Shippers_Id() == 1L
        && shipper.getCompanyName().equals("Svensson-Karlsson")
        && shipper.getPhone().equals("070-569-3764")));
        verify(shippersRepo, times(1)).save(argThat(shipper -> shipper.getExternal_Shippers_Id() == 2L));
        verify(shippersRepo, times(1)).save(argThat(shipper -> shipper.getExternal_Shippers_Id() == 3L));


               /* "id": 1,
                "email": "birgitta.olsson@hotmail.com",
                "companyName": "Svensson-Karlsson",
                "contactName": "Erik Östlund",
                "contactTitle": "painter",
                "streetAddress": "Järnvägsallén 955",
                "city": "Gävhult",
                "postalCode": "07427",
                "country": "Sverige",
                "phone": "070-569-3764",
                "fax": "2634-25376"*/


    }

}
