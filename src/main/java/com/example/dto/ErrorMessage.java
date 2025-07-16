package com.example.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorMessage {

    private Integer status;
    private String message;
}
