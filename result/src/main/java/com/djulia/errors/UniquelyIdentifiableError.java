package com.djulia.errors;

public interface UniquelyIdentifiableError extends Error {
    String getMessage();

    default String getErrorType(){
        return this.getClass().getSimpleName();
    }
}
