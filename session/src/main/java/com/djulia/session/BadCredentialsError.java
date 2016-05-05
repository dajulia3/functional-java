package com.djulia.session;

import com.djulia.errors.UniquelyIdentifiableError;

public class BadCredentialsError implements UniquelyIdentifiableError{
    @Override
    public String getMessage() {
        return "Bad username or password";
    }
}
