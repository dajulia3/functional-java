package com.djulia.restsupport.internalrestclient;

import com.djulia.errors.Error;
import com.djulia.result.Result;

public class RestClient {
    public <T> Result<T, Error> getSomeResource(){
        return Result.success("yaaay");
    }
}
