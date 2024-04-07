package com.sp1222.language.daybyday.api.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class DuplicatePropertyException extends RuntimeException {
    /**
     * Email that is duplicated.
     */
    private final String property;
}
