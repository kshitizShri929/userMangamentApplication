package com.keenable.user_magnagement_application.controller;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse {
    private String massage;
    private Object userData;
}
