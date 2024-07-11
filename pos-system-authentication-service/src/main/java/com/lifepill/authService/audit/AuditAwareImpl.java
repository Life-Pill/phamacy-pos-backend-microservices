package com.lifepill.authService.audit;

import lombok.NonNull;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Implementation of AuditorAware interface to provide the current auditor of the application.
 */
@Component("auditorAwareImpl")
public class AuditAwareImpl implements AuditorAware<String> {

    /**
     * This method is used to get the current auditor of the application
     * @return the current auditor
     */
    @NonNull
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("PosSystem");
    }
}
