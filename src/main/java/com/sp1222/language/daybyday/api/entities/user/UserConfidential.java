package com.sp1222.language.daybyday.api.entities.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * Confidential User entity for handling password-related data.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@JsonIgnoreProperties(ignoreUnknown = true)
@Valid
public class UserConfidential extends BaseUser {

    /**
     * Hashed password.
     */
    @Column(name = "hashed", nullable = false)
    @NotBlank(message = "hashed must contain a value")
    private String hashed;
}
