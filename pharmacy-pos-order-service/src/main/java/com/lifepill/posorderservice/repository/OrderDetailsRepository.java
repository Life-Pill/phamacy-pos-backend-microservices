package com.lifepill.posorderservice.repository;

import com.lifepill.posorderservice.entity.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

/**
 * The interface Order details repository.
 */
@Repository
@EnableJpaRepositories
public interface OrderDetailsRepository extends JpaRepository<OrderDetails,Integer> {
}
