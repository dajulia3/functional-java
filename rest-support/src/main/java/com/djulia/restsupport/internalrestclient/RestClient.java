package com.djulia.restsupport.internalrestclient;

import com.djulia.result.Result;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

public class RestClient {
    private final RestTemplate restTemplate;

    public RestClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public <T> Result<T, RestClientError> getSomeResource(String url, Class<T> responseType){
        try {
            return Result.success(restTemplate.getForObject(url, responseType));
        }catch (HttpClientErrorException e){
            HttpClientError httpClientError = new HttpClientError(e.getStatusCode().value(), e.getResponseBodyAsString());
            return Result.failure(RestClientError.httpClientError(httpClientError));
        }
    }
}
