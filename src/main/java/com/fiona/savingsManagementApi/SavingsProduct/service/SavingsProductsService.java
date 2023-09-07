package com.fiona.savingsManagementApi.SavingsProduct.service;

import com.fiona.savingsManagementApi.Exceptions.ResourceNotFoundException;
import com.fiona.savingsManagementApi.SavingsProduct.model.SavingsProductModel;
import com.fiona.savingsManagementApi.SavingsProduct.payload.SavingsProductPayload;
import com.fiona.savingsManagementApi.SavingsProduct.repository.SavingsProductsRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class SavingsProductsService {
    private SavingsProductsRepository savingsProductsRepository;
    private ModelMapper modelMapper;
    //create savings products;
    public SavingsProductModel createSavingsProduct(SavingsProductPayload payload){
        SavingsProductModel savingsProductModel =  modelMapper.map(payload,SavingsProductModel.class);

        return savingsProductsRepository.save(savingsProductModel);
    }

    //getAllSavingsProducts
    public List<SavingsProductModel> getAllSavingsProducts(){
       return savingsProductsRepository.findAll();
    }

    //getOneById
    public SavingsProductModel getOneSavingsProduct(UUID id){
        return  findSavingsProductById(id);
    }

    //update
    public SavingsProductModel updateSavingsProduct(SavingsProductPayload payload, UUID id){
        SavingsProductModel savingsProductModel = findSavingsProductById(id);
        savingsProductModel.setName(payload.getName());
        return savingsProductsRepository.save(savingsProductModel);
    }

    // delete
    //you cant delete a product for now

    public SavingsProductModel findSavingsProductById(UUID id){
        SavingsProductModel savingsProductModel = savingsProductsRepository.findById(id).orElseThrow(()->
            new ResourceNotFoundException("savingsProduct does not exists"));
        return savingsProductModel;
    }
}
