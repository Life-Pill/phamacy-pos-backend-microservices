package com.lifePill.SupplierService.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class MessageController {

    @Value("${message}")
    private String message;

    @GetMapping("supplier/message")
    public String getMessage() {
        return message;
    }
}
