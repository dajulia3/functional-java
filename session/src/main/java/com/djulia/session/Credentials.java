package com.djulia.session;

import com.google.auto.value.AutoValue;

@AutoValue
//@AutoValue.Builder
public abstract class Credentials {
//    static Credentials create(String username, String password) {
//        return new AutoValue_Credentials(username, password);
//    }

    public static Builder builder() {
        return new AutoValue_Credentials.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder username(String username);
        public abstract Builder password(String password);
        public abstract Credentials build();
    }

    public abstract String getUsername();
    public abstract String getPassword();
}
