package com.djulia.result;


import org.junit.Test;

import java.util.ArrayList;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class ResultTest {

    private Function<Number, Result<String, Exception>> numberToStringResultFunc = n ->
            Result.success(String.valueOf(n));
    ;
    private Function<Number, String> numberToStringFunc = n -> String.valueOf(n);
    private Result<String, Exception> successAbc = Result.<String, Exception>success("abc");
    private Result<Integer, String> failureAbc = Result.<Integer, String>failure("abc");
    private Result<String, Exception> failureOopsException = Result.<String, Exception>failure(new RuntimeException("oops."));

    @Test
    public void testMapSuccess() throws Exception {
        Result<String, Exception> successResult = successAbc
                .mapSuccess(String::length)
                .mapSuccess(numberToStringFunc);
        Result<Integer, String> failureResult = failureAbc
                .mapSuccess(num -> num * 0);

        assertThat(successResult).isEqualTo(Result.success("3"));
        assertThat(failureResult).isEqualTo(Result.failure("abc"));
    }

    @Test
    public void testMapFailure() {
        Result<Integer, Integer> failureResult = failureAbc
                .mapFailure(Object::toString)
                .mapFailure(String::length);

        Result<String, Integer> successResult = Result.<String, Integer>success("abc")
                .mapFailure(num -> num * 0);

        assertThat(failureResult).isEqualTo(Result.failure(3));
        assertThat(successResult).isEqualTo(Result.success("abc"));
    }

    @Test
    public void testMap() {
        Integer successResult = successAbc
                .map(s -> 3, o -> 99);
        Integer failureResult = failureAbc
                .map(s -> 3, o -> 99);

        assertThat(successResult).isEqualTo(3);
        assertThat(failureResult).isEqualTo(99);
    }

    @Test
    public void testFlatMapSuccess() {
        Result<String, Exception> successToMappedSuccess = successAbc
                .flatMapSuccess(n -> Result.success(3))
                .flatMapSuccess(n -> Result.success(n * 2))
                .flatMapSuccess(numberToStringResultFunc);

        Result<String, Exception> successToFailure = successAbc
                .flatMapSuccess(n -> failureOopsException);

        Result<Integer, Exception> failureToSuccess = failureOopsException
                .flatMapSuccess(s -> Result.success(3));

        Result<Integer, Exception> failureToFailure = failureOopsException
                .flatMapSuccess(s -> Result.success(3))
                .flatMapSuccess(f -> Result.failure(new RuntimeException("some other exception")));

        assertThat(successToMappedSuccess).isEqualTo(Result.success("6"));
        assertThat(successToFailure).isEqualTo(failureOopsException);
        assertThat(failureToSuccess).isEqualTo(failureOopsException);
        assertThat(failureToFailure).isEqualTo(failureOopsException);
    }

    @Test
    public void testApply(){
        ArrayList<Boolean> successRecorder = new ArrayList<>();
        ArrayList<Boolean> failureRecorder = new ArrayList<>();

        Result.success("hey").apply(s-> successRecorder.add(true), f -> successRecorder.add(false));
        Result.failure("hey").apply(s-> failureRecorder.add(true), f -> failureRecorder.add(false));

        assertThat(successRecorder).containsExactly(true);
        assertThat(failureRecorder).containsExactly(false);
    }

    @Test
    public void testFlatMapFailure() {
        Result<String, Exception> successToSuccessResult = failureOopsException
                .flatMapFailure(err -> Result.failure(3))
                .flatMapFailure(n -> Result.failure(n * 2))
                .flatMapFailure(numberToStringResultFunc);

        Result<String, Exception> failureToSuccessResult = successAbc
                .flatMapFailure(n -> failureOopsException);

        Result<String, Exception> successToFailureResult = successAbc
                .flatMapFailure(err -> failureOopsException);

        Result<String, Integer> failureToMappedFailure = failureOopsException
                .flatMapFailure(err -> Result.failure(22));
        assertThat(successToSuccessResult).isEqualTo(Result.success("6"));
        assertThat(successToFailureResult).isEqualTo(successAbc);
        assertThat(failureToSuccessResult).isEqualTo(successAbc);
        assertThat(failureToMappedFailure).isEqualTo(Result.failure(22));
    }

    @Test
    public void fold() {
        Boolean successResult = successAbc.fold(s -> true, f -> false);
        Boolean failureResult = failureAbc.fold(s -> true, f -> false);
        String successWithSuperTypeFunctionCompiles = Result.<Integer,Double>success(1).fold(numberToStringFunc , numberToStringFunc);
        String failureWithSuperTypeFunctionCompiles = Result.<Integer,Double>failure(2.0).fold(numberToStringFunc , numberToStringFunc);

        assertThat(successResult).isEqualTo(true);
        assertThat(failureResult).isEqualTo(false);
    }
}