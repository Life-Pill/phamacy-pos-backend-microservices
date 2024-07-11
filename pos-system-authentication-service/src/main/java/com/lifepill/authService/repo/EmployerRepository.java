package com.lifepill.authService.repo;

import com.lifepill.authService.entity.Employer;
import com.lifepill.authService.entity.enums.Role;
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

    /**
     * Find by is active status equals list.
     *
     * @param activeState the active state
     * @return the list
     */
    List<Employer> findByIsActiveStatusEquals(boolean activeState);

    /**
     * Exists all by employer email boolean.
     *
     * @param employerEmail the employer email
     * @return the boolean
     */
    boolean existsAllByEmployerEmail(String employerEmail);

    /**
     * Find all by role list.
     *
     * @param role the role
     * @return the list
     */
    List<Employer> findAllByRole(Role role);

    /**
     * Find by employer email optional.
     *
     * @param employerEmail the employer email
     * @return the optional
     */
    Optional<Employer> findByEmployerEmail(String employerEmail);


}
