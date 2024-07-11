package com.lifepill.posinventoryservice.repository;

import com.lifepill.posinventoryservice.entity.ItemCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

/**
 * The interface Item category repository.
 */
@Repository
@EnableJpaRepositories
public interface ItemCategoryRepository extends JpaRepository<ItemCategory, Long> {

}
