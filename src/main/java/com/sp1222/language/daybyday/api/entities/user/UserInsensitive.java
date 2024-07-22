package com.sp1222.language.daybyday.api.entities.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * Insensitive User entity for handling insensitive data.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@JsonIgnoreProperties(ignoreUnknown = true)
@Valid
public class UserInsensitive extends BaseUser {

    /**
     * First name.
     */
    @Column(name = "firstname", nullable = false)
    @NotBlank(message = "First name cannot be blank")
    private String firstname;
}
