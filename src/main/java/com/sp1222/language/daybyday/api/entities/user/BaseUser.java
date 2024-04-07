package com.sp1222.language.daybyday.api.entities.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

/**
 * Base User entity.
 */
@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@Data
public abstract class BaseUser {

    /**
     * Id for entities.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @PositiveOrZero
    private long id;

    /**
     * Username.
     */
    @Column(name = "username", unique = true, nullable = false, updatable = false)
    @NotBlank(message = "Username must contain characters")
    private String username;
}
