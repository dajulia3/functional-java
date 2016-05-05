package com.djulia.errors;

public class UnexpectedError implements UniquelyIdentifiableError {
    @Override
    public String getMessage() {
        return "That was unexpected.";
    }
}
