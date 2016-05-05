package com.djulia.session;

import com.djulia.errors.UniquelyIdentifiableError;

public class EmailNotVerifiedError implements UniquelyIdentifiableError {
    @Override
    public String getMessage() {
        return "Your email is not yet confirmed. Please click the link in the email that we sent you.";
    }
}
