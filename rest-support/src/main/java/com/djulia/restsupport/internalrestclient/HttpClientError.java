package com.djulia.restsupport.internalrestclient;

public class HttpClientError {
    private final int statusCode;
    private final String body;

    public HttpClientError(int statusCode, String body) {
        this.statusCode = statusCode;
        this.body = body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HttpClientError that = (HttpClientError) o;

        if (statusCode != that.statusCode) return false;
        return body != null ? body.equals(that.body) : that.body == null;

    }

    @Override
    public int hashCode() {
        int result = statusCode;
        result = 31 * result + (body != null ? body.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "HttpClientError{" +
                "statusCode=" + statusCode +
                ", body='" + body + '\'' +
                '}';
    }
}
