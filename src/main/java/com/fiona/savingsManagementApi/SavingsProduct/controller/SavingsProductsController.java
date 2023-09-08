package com.fiona.savingsManagementApi.SavingsProduct.controller;

import com.fiona.savingsManagementApi.SavingsProduct.model.SavingsProductModel;
import com.fiona.savingsManagementApi.SavingsProduct.payload.SavingsProductPayload;
import com.fiona.savingsManagementApi.SavingsProduct.service.SavingsProductsService;
import com.fiona.savingsManagementApi.utils.APiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.fiona.savingsManagementApi.utils.APIResponseBuilder.buildResponseEntity;

@RestController
@RequestMapping("api/v1/savings/savingsProducts")
@AllArgsConstructor
public class SavingsProductsController {

    private SavingsProductsService savingsProductsService;

    @PostMapping("/createSavingsProduct")
    public ResponseEntity<APiResponse> createSavingsProduct(@Valid @RequestBody  SavingsProductPayload payload, HttpServletRequest request){

        SavingsProductModel savingsProductModel = savingsProductsService.createSavingsProduct(payload);
        return buildResponseEntity(HttpStatus.CREATED,savingsProductModel,request.getRequestURI());
    }

    @GetMapping("/getAllSavingsProducts")
    public ResponseEntity<APiResponse> getAllSavingsProduct(HttpServletRequest request){
        List<SavingsProductModel> savingsProductModelList = savingsProductsService.getAllSavingsProducts();
        return buildResponseEntity(HttpStatus.OK,savingsProductModelList,request.getRequestURI());
    }

    @GetMapping("/getOneSavingProduct/{id}")
    public  ResponseEntity<APiResponse> getOneSavingsProduct( @PathVariable UUID id,HttpServletRequest request){
        SavingsProductModel savingsProductModel = savingsProductsService.getOneSavingsProduct(id);
        return buildResponseEntity(HttpStatus.OK,savingsProductModel,request.getRequestURI());
    }

    @PutMapping("updateSavingsProduct/{id}")
    public ResponseEntity<APiResponse> updateSavingsProduct(@PathVariable UUID id,@RequestBody SavingsProductPayload payload, HttpServletRequest request){
        SavingsProductModel savingsProductModel = savingsProductsService.updateSavingsProduct(payload,id);

        return buildResponseEntity(HttpStatus.OK,savingsProductModel,request.getRequestURI());
    }
}
