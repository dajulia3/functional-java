package com.djulia.restsupport.internalrestclient;

import java.util.Optional;
import java.util.function.Function;

public class RestClientError {
    private final Optional<ConnectionError> connectionError;
    private final Optional<DeserializationError> deserializationError;
    private final Optional<HttpClientError> httpClientError;

    private RestClientError(Optional<ConnectionError> connectionError, Optional<DeserializationError> deserializationError, Optional<HttpClientError> httpClientError) {
        this.connectionError = connectionError;
        this.deserializationError = deserializationError;
        this.httpClientError = httpClientError;
    }

    public static RestClientError connectionError(ConnectionError connectionError){
        return new RestClientError(Optional.of(connectionError), Optional.empty(), Optional.empty());
    }

    public static RestClientError deserializationError(DeserializationError deserializationError){
        return new RestClientError(Optional.empty(), Optional.of(deserializationError), Optional.empty());
    }
    public static RestClientError httpClientError(HttpClientError httpClientError){
        return new RestClientError(Optional.empty(), Optional.empty(), Optional.of(httpClientError));
    }

    public <T> T fold(Function<? super DeserializationError, T> deserializationErrorFn,
                      Function<? super ConnectionError, T> connectionErrorFn,
                      Function<? super HttpClientError, T> httpClientErrorFn
                      ){
        if(connectionError.isPresent()){
            return connectionErrorFn.apply(connectionError.get());
        } else if(deserializationError.isPresent()) {
            return deserializationErrorFn.apply(deserializationError.get());
        }else{
            return httpClientErrorFn.apply(httpClientError.get());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RestClientError that = (RestClientError) o;

        if (connectionError != null ? !connectionError.equals(that.connectionError) : that.connectionError != null)
            return false;
        if (deserializationError != null ? !deserializationError.equals(that.deserializationError) : that.deserializationError != null)
            return false;
        return httpClientError != null ? httpClientError.equals(that.httpClientError) : that.httpClientError == null;

    }

    @Override
    public int hashCode() {
        int result = connectionError != null ? connectionError.hashCode() : 0;
        result = 31 * result + (deserializationError != null ? deserializationError.hashCode() : 0);
        result = 31 * result + (httpClientError != null ? httpClientError.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "RestClientError{" +
                "connectionError=" + connectionError +
                ", deserializationError=" + deserializationError +
                ", httpClientError=" + httpClientError +
                '}';
    }
}
