package com.example.dto;

import lombok.*;
import lombok.Builder;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductResponse {

    private List<ProductDto> products;
    private long totalelements;
    private int totalpages;
    private Boolean isFirst;
    private Boolean isLast;
    private int pageNo;
    private int pageSize;




}

