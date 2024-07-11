package com.lifepill.posorderservice.repository;

import com.lifepill.posorderservice.entity.Order;
import com.lifepill.posorderservice.entity.PaymentDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

/**
 * The interface Payment repository.
 */
@Repository
@EnableJpaRepositories
public interface PaymentRepository extends JpaRepository<PaymentDetails,Long> {
    PaymentDetails findByOrders(Order order);
}
