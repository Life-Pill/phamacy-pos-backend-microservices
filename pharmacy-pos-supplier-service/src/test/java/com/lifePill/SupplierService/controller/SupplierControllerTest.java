//package com.lifePill.SupplierService.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.lifePill.SupplierService.controller.SupplierController;
//import com.lifePill.SupplierService.dto.SupplierDTO;
//import com.lifePill.SupplierService.service.SupplierService;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//
//@WebMvcTest(SupplierController.class)
//public class SupplierControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Mock
//    private SupplierService supplierService;
//
//    @InjectMocks
//    private SupplierController supplierController;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Test
//    public void testSaveSupplier() throws Exception {
//        SupplierDTO supplierDTO = new SupplierDTO(1L, "Test Supplier", "1234567890", "test@example.com", "Supplier description");
//
//        when(supplierService.saveSupplier(any(SupplierDTO.class))).thenReturn(supplierDTO);
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/lifepill/v1/supplier/saved")
//                        .content(objectMapper.writeValueAsString(supplierDTO))
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isCreated())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(201))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Supplier Saved Successfully"));
//    }
//
//}
