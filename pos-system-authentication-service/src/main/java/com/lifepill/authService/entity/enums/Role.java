package com.lifepill.authService.entity.enums;

import lombok.RequiredArgsConstructor;

import java.util.Set;

import static com.lifepill.authService.entity.enums.Permission.*;

/**
 * The enum Role.
 */
@RequiredArgsConstructor
public enum Role {
    /**
     * Owner role.
     */
// CASHIER,MANAGER,OWNER,OTHER
   OWNER(
            Set.of(
            OWNER_READ,
            OWNER_CREATE,
            CASHIER_READ,
            CASHIER_CREATE,
            OTHER_READ,
            OTHER_CREATE
            )
  ),

    /**
     * Manager role.
     */
    MANAGER(
            Set.of(
                    MANAGER_READ,
                    MANAGER_CREATE,
                    CASHIER_READ,
                    CASHIER_CREATE,
                    OTHER_READ,
                    OTHER_CREATE
            )
    ),
    /**
     * Cashier role.
     */
    CASHIER(
            Set.of(
            CASHIER_READ,
            CASHIER_CREATE
            )
    ),

    /**
     * Other role.
     */
    OTHER(
            Set.of(
            OTHER_READ,
            OTHER_CREATE
            )
    )
    ;

    private final Set<Permission> permissions;

}
