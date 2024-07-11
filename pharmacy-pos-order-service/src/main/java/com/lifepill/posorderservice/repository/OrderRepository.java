package com.lifepill.posorderservice.repository;


import com.lifepill.posorderservice.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;


/**
 * The interface Order repository.
 */
@Repository
@EnableJpaRepositories
public interface OrderRepository extends JpaRepository<Order,Long> {

}