package com.djulia.errors;

import java.util.Set;

public class ValidationError implements UniquelyIdentifiableError{
    private final Set<FieldValidationError> fieldValidationErrors;

    public ValidationError(Set<FieldValidationError> fieldValidationErrors) {
        this.fieldValidationErrors = fieldValidationErrors;
    }

    @Override
    public String getMessage() {
        return "Validation Error.";
    }

    public static class FieldValidationError {
        private final String field;
        private final Set<String> errorMessages;

        public FieldValidationError(String field, Set<String> errorMessages) {
            this.field = field;
            this.errorMessages = errorMessages;
        }
    }

    public Set<FieldValidationError> getFieldValidationErrors(){
        return fieldValidationErrors;
    }
}
