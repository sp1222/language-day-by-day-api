package com.sp1222.language.daybyday.api.entities.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Users entity.
 */
@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users", uniqueConstraints = @UniqueConstraint(name = "unique_user_props", columnNames = {"username", "email"}))
@JsonIgnoreProperties(ignoreUnknown = true)
@Valid
public class Users {

    /**
     * Id for entities.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @PositiveOrZero
    private long id;

    /**
     * First name.
     */
    @Column(name = "firstname", nullable = false)
    @NotBlank(message = "First name cannot be blank")
    private String firstname;

    /**
     * Username.
     */
    @Column(name = "username", unique = true, nullable = false, updatable = false)
    @NotBlank(message = "Username must contain characters")
    private String username;

    /**
     * Email.
     */
    @Column(name = "email", unique = true, nullable = false)
    @Email(message = "Invalid email format", regexp = "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-za-z]{2,3}")
    private String email;

    /**
     * Hashed password.
     */
    @Column(name = "hashed", nullable = false)
    @NotBlank(message = "Value must not be null")
    private String hashed;
}
