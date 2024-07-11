package com.lifepill.posorderservice.Audit;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditorAwareImpl implements AuditorAware<String> {

    @NotNull
    @Override
    public Optional<String> getCurrentAuditor() {
        // TODO: Replace this with actual logged-in user fetching logic
        return Optional.of("PosOrderService");
    }

}