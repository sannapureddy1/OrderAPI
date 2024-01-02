package com.orderAPI.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.orderAPI.DTO.InventoryUpdateRequest;
import com.orderAPI.DTO.ResponseDTO;
import com.orderAPI.entities.Inventory;
import com.orderAPI.services.InventoryService;

import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;
    
	/*
	  PathVariable - none
	  RequestURL - http://localhost:9009/inventory/all
	*/
    @GetMapping("/all")
    public List<Inventory> findAll() {
        return inventoryService.findAll();
    }

    /*
	  PathVariable - Long {id}
	  RequestURL - http://localhost:9009/inventory/get-by-id/{id}
	*/
    @GetMapping("/get-by-id/{id}")
    public Inventory findById(@PathVariable Long id) {
    	return inventoryService.findById(id);
    }

    /*
	  PathVariable - String {name}
	  RequestURL - http://localhost:9009/inventory/get-by-name/{name}
	*/
    @GetMapping("/get-by-name/{name}")
    public Inventory findByName(@PathVariable String name) {
    	return inventoryService.findByName(name);
    }
    
    /*
	  RequestParam - String status
	  RequestURL - http://localhost:9009/inventory/get-by-status?status={value}
	*/
    @GetMapping("/get-by-status")
    public List<Inventory> findByStatus(@RequestParam String status) {
        return inventoryService.findByStatus(status);
    }
    
    /*
	  RequestParam - String status
	  RequestURL - http://localhost:9009/inventory/count-by-status?status={value}
	*/
    @GetMapping("/count-by-status")
    public Long countByStatus(@RequestParam String status) {
        return inventoryService.countByStatus(status);
    }

    /*
	  RequestBody - Inventory temp
	  RequestURL - http://localhost:9009/inventory/add
	*/
    @PostMapping("/add")
    public Inventory save(@RequestBody Inventory temp) {
        return inventoryService.add(temp);
    }
    
    /*
	  RequestBody - List<Inventory> temp
	  RequestURL - http://localhost:9009/inventory/add-all
	*/
    @PostMapping("/add-all")
    public List<Inventory> addAll(@RequestBody List<Inventory> productList){
    	return inventoryService.addAll(productList);
    }
    
    /*
      PathVariable - String {name}
	  RequestBody - InventoryUpdateRequest temp
	  RequestURL - http://localhost:9009/inventory/update/{name}
	*/
    @PutMapping("/update/{name}")
    public Inventory update(@PathVariable String name, @RequestBody InventoryUpdateRequest temp) {
        return inventoryService.update(name, temp);
    }

    /*
	  PathVariable - Long {id}
	  RequestURL - http://localhost:9009/inventory/delete-by-id/{id}
	*/
    @DeleteMapping("/delete-by-id/{id}")
    public String deleteById(@PathVariable Long id) {
    	return inventoryService.deleteById(id);
    }
    
    /*
	  PathVariable - String {name}
	  RequestURL - http://localhost:9009/inventory/delete-by-name/{name}
	*/
    @DeleteMapping("/delete-by-name/{name}")
    public String deleteByName(@PathVariable String name) {
    	return inventoryService.deleteByName(name);
    }
    
    /*
      PathVariable - String {name}
      RequestParam - Integer soldQuantity
      RequestURL - http://localhost:9009/inventory/sell/{name}?soldQuantity={value} 
    */
    @PostMapping("/sell/{name}")
    public ResponseDTO sell(@PathVariable String name, @RequestParam Integer soldQuantity) {
        return inventoryService.sell(name,soldQuantity);
    }
    
}
