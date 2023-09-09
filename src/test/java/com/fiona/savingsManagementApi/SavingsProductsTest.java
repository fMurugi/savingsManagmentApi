package com.fiona.savingsManagementApi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiona.savingsManagementApi.SavingsProduct.model.SavingsProductModel;
import com.fiona.savingsManagementApi.SavingsProduct.payload.SavingsProductPayload;
import com.fiona.savingsManagementApi.SavingsProduct.repository.SavingsProductsRepository;
import com.fiona.savingsManagementApi.SavingsProduct.service.SavingsProductsService;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SavingsProductsTest {
    @MockBean
    private SavingsProductsRepository savingsProductsRepository;
    @Autowired
    private SavingsProductsService savingsProductsService;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void createSavingsProductTest() throws Exception{
        SavingsProductPayload savingsProductPayload = new SavingsProductPayload();
        savingsProductPayload.setName("vacation");

        SavingsProductModel savingsProductModel = new SavingsProductModel();
        UUID id = UUID.randomUUID();
        savingsProductModel.setSavingProductId(id);
        savingsProductModel.setName("vacation");

        when(savingsProductsRepository.save(any())).thenReturn(savingsProductModel);

        SavingsProductModel createdSavingsProduct = savingsProductsService.createSavingsProduct(savingsProductPayload);

        assertEquals(id,createdSavingsProduct.getSavingProductId());
        assertEquals("vacation",createdSavingsProduct.getName());

    }

    @Test
    public void getAllSavingsProductTest(){
        SavingsProductModel savingsProductModel = new SavingsProductModel();
        UUID id = UUID.randomUUID();
        savingsProductModel.setSavingProductId(id);
        savingsProductModel.setName("vacation");

        when(savingsProductsRepository.findAll()).thenReturn(Arrays.asList(savingsProductModel));

        List<SavingsProductModel> savingsProductModelList = savingsProductsService.getAllSavingsProducts();
        assertNotNull(savingsProductModelList);

    }

    @Test
    public void getOneSavingsProductTest(){
        SavingsProductModel savingsProductModel = new SavingsProductModel();
        UUID id = UUID.randomUUID();
        savingsProductModel.setSavingProductId(id);
        savingsProductModel.setName("vacation");

        when(savingsProductsRepository.findById(id)).thenReturn(Optional.of(savingsProductModel));

        SavingsProductModel retrievedProduct = savingsProductsService.getOneSavingsProduct(id);

        assertEquals(retrievedProduct,savingsProductModel);
    }

   @Test
    public void updateSavingsProductTest(){
       SavingsProductModel savingsProductModel = new SavingsProductModel();
       UUID id = UUID.randomUUID();
       savingsProductModel.setSavingProductId(id);
       savingsProductModel.setName("vacation");

       SavingsProductPayload updatePayload = new SavingsProductPayload();
       updatePayload.setName("Updated Vacation Savings");

       when(savingsProductsRepository.findById(id)).thenReturn(Optional.of(savingsProductModel));
       when(savingsProductsRepository.save(any())).thenReturn(savingsProductModel);
       SavingsProductModel updatedProduct = savingsProductsService.updateSavingsProduct(updatePayload,id);


       assertEquals(id,updatedProduct.getSavingProductId());
       assertEquals("Updated Vacation Savings",updatedProduct.getName());
   }
}
