//package com.lifePill.SupplierService.repository;
//
//import com.lifePill.SupplierService.entity.Suppliers;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@DataJpaTest
//public class SupplierRepositoryTest {
//
//    @Autowired
//    private SupplierRepository supplierRepository;
//
//    @Test
//    public void testSaveSupplier() {
//        Suppliers supplier = new Suppliers(1L, "Test Supplier", "1234567890", "test@example.com", "Supplier description");
//        supplierRepository.save(supplier);
//
//        Suppliers savedSupplier = supplierRepository.findById(1L).orElse(null);
//        assertNotNull(savedSupplier);
//        assertEquals("Test Supplier", savedSupplier.getSupplierName());
//    }
//
//    @Test
//    public void testExistsBySupplierEmail() {
//        Suppliers supplier = new Suppliers(1L, "Test Supplier", "1234567890", "test@example.com", "Supplier description");
//        supplierRepository.save(supplier);
//
//        boolean exists = supplierRepository.existsBySupplierEmail("test@example.com");
//        assertTrue(exists);
//    }
//
//    @Test
//    public void testFindBySupplierId() {
//        Suppliers supplier = new Suppliers(1L, "Test Supplier", "1234567890", "test@example.com", "Supplier description");
//        supplierRepository.save(supplier);
//
//        Suppliers foundSupplier = supplierRepository.findBySupplierId(1L);
//        assertNotNull(foundSupplier);
//        assertEquals("Test Supplier", foundSupplier.getSupplierName());
//    }
//
//    @Test
//    public void testDeleteSupplier() {
//        Suppliers supplier = new Suppliers(1L, "Test Supplier", "1234567890", "test@example.com", "Supplier description");
//        supplierRepository.save(supplier);
//
//        supplierRepository.deleteById(1L);
//
//        Suppliers deletedSupplier = supplierRepository.findById(1L).orElse(null);
//        assertNull(deletedSupplier);
//    }
//
//}
