package com.sp1222.language.daybyday.api.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class NotFoundByNameException extends RuntimeException {
    /**
     * Username of the Patron not found.
     */
    private final String name;
}
