package com.lifepill.employeeService.repository;


import com.lifepill.employeeService.entity.Employer;
import com.lifepill.employeeService.entity.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * The interface Employer repository.
 */
@Repository
@EnableJpaRepositories
public interface EmployerRepository extends JpaRepository<Employer,Long> {

    Optional<Employer> findByEmployerEmail(String employerEmail);

    boolean existsAllByEmployerEmail(String employerEmail);

    //TODO: resolve IsActiveStatus to new updated field
    List<Employer> findByIsActiveStatusEquals(boolean activeState);

    List<Employer> findByBranchId(int branchId);

    Optional<Employer> findByBranchIdAndRole(int branchId, Role role);

}
