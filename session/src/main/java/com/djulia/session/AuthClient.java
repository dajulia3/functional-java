package com.djulia.session;

import com.djulia.errors.Error;
import com.djulia.restsupport.internalrestclient.RestClient;
import com.djulia.result.Result;

public class AuthClient {
    private final RestClient restClient;

    public AuthClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public Result<Boolean, Error> getUserAuth(com.djulia.session.Credentials credentials){
        return null;
//        return restClient.getSomeResource().fold(success);
    }
}
