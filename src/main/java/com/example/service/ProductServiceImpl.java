package com.example.service;

import com.example.dto.ProductDto;
import com.example.dto.ProductResponse;
import com.example.model.Product;
import com.example.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper mapper;
    @Override
    public Boolean saveProduct(ProductDto productDto) {
//        Product product = new Product();
//        product.setId(productDto.getId());
//        product.setName(productDto.getName());
//        product.setDescription(productDto.getDescription());
//        product.setPrice(productDto.getPrice());
//        product.setQuantity(productDto.getQuantity());

        Product product = mapper.map(productDto,Product.class);
        Product save = productRepository.save(product);
        if(ObjectUtils.isEmpty(save)){
            return false;
        }
        return true;
    }

    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> productlist = productRepository.findAll();
        List<ProductDto> productDtolist = productlist.stream().map(product -> mapper.map(product,ProductDto.class))
                .collect(Collectors.toList());
        return productDtolist;
    }

    @Override
    public ProductDto getProductById(Integer id) {
        Optional<Product> findByProduct = productRepository.findById(id);
        if(findByProduct.isPresent()){
            Product product = findByProduct.get();
            ProductDto productDto = mapper.map(product,ProductDto.class);
            return productDto;
        }
        return null;
    }

    @Override
    public Boolean deleteProduct(Integer id) {
        Optional<Product> findByProduct = productRepository.findById(id);
        if(findByProduct.isPresent()){
            Product product = findByProduct.get();
            productRepository.delete(product);
            return true;
        }
        return false;
    }

    @Override
    public ProductResponse getProductwithPagination(int pageNo, int pageSize, String sortBy, String sortDir) {


        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable =  PageRequest.of(pageNo,pageSize,sort);
        Page<Product> page = productRepository.findAll(pageable);

        List<Product> products = page.getContent();
        List<ProductDto> productsDtos = products.stream().map(prod -> mapper.map(prod,ProductDto.class)).toList();


        long totalelements = page.getTotalElements();
        int totalPages = page.getTotalPages();
        boolean first = page.isFirst();
        boolean last = page.isLast();

        ProductResponse productResponse = ProductResponse.builder().products(productsDtos).totalelements(totalelements)
                .totalpages(totalPages).isFirst(first).isLast(last).pageNo(pageNo).pageSize(pageSize).build();

        return productResponse;
    }
}
