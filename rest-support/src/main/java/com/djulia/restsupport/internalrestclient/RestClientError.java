package com.djulia.restsupport.internalrestclient;

import java.util.Optional;
import java.util.function.Function;

public class RestClientError {
    private final Optional<ConnectionError> connectionError;
    private final Optional<DeserializationError> deserializationError;

    private RestClientError(Optional<ConnectionError> connectionError, Optional<DeserializationError> deserializationError) {
        this.connectionError = connectionError;
        this.deserializationError = deserializationError;
    }

    public static RestClientError ConnectionError(ConnectionError connectionError){
        return new RestClientError(Optional.of(connectionError), Optional.empty());
    }

    public static RestClientError DeserializationError(DeserializationError deserializationError){
        return new RestClientError(Optional.empty(), Optional.of(deserializationError));
    }

    public <T> T fold(Function<? super DeserializationError, T> deserializationErrorFn,
                      Function<? super ConnectionError, T> connectionErrorFn){
        if(connectionError.isPresent()){
            return connectionErrorFn.apply(connectionError.get());
        }
        return deserializationErrorFn.apply(deserializationError.get());
    }
}
