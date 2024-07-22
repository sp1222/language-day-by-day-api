package com.sp1222.language.daybyday.api.entities.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.*;

/**
 * Sensitive User entity for handling sensitive data.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users", uniqueConstraints = @UniqueConstraint(name = "unique_email", columnNames = {"email"}))
@JsonIgnoreProperties(ignoreUnknown = true)
@Valid
public class UserSensitive extends BaseUser {

    /**
     * Email.
     */
    @Column(name = "email", unique = true, nullable = false)
    @Email(message = "Invalid email format", regexp = "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-za-z]{2,3}")
    private String email;
}
