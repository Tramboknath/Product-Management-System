package com.example.controller;

import com.example.dto.ProductDto;
import com.example.dto.ProductResponse;
import com.example.model.Product;
import com.example.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;


    @PostMapping("/save-product")
    public ResponseEntity<?> saveProduct(@RequestBody ProductDto productDto){

        try {
            Boolean saveProduct = productService.saveProduct(productDto);
            if(!saveProduct){
                return new ResponseEntity<>("Product not saved",HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }


        return new ResponseEntity<>("saved success", HttpStatus.CREATED);
    }

    @GetMapping("/get-product")
    public ResponseEntity<?> getProducts(){
        List<ProductDto> allProducts = null;
        try {
             allProducts = productService.getAllProducts();
            if(CollectionUtils.isEmpty(allProducts)){

                return new ResponseEntity<>("No Content present",HttpStatus.OK);
            }
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }


        return new ResponseEntity<>(allProducts, HttpStatus.OK);
    }

    @GetMapping("/get-product/{id}")
    public ResponseEntity<?> getProducts(@PathVariable Integer id){
        ProductDto product = null;
        try {
            product = productService.getProductById(id);
            if(ObjectUtils.isEmpty(product)){
                return new ResponseEntity<>("Product not found with id="+id, HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }


        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<?> deleteById(@PathVariable(name = "id") Integer id){
        Boolean deleteProduct = null;
        try {
            deleteProduct = productService.deleteProduct(id);
            if(!deleteProduct){
                return new ResponseEntity<>("Product not Deleted", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }


        return new ResponseEntity<>("Delete success", HttpStatus.OK);
    }

    @GetMapping("/page-products")
    public ResponseEntity<?> getProductsPaginate(@RequestParam(name = "pageNo", defaultValue = "0") int pageNo,
                                                 @RequestParam(name = "pageSize", defaultValue = "2") int pageSize,
                                                 @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
                                                 @RequestParam(name = "sortDir", defaultValue = "asc") String sortDir) {
        ProductResponse productResponse = null;
		String name = null;
		name.toUpperCase();
        try {

            productResponse = productService.getProductwithPagination(pageNo, pageSize, sortBy, sortDir);
            if (ObjectUtils.isEmpty(productResponse)) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }


}
