package com.djulia;

import org.junit.Test;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;

public class ResultTest {

    private Function<Number, Result<String, Exception>> numberToStringResultFunc = n ->
            Result.success(String.valueOf(n));;
    private Function<Number, String> numberToStringFunc = n -> String.valueOf(n);

    @Test
    public void testMapSuccess() throws Exception {
        Result<String, Exception> successResult = Result.<String, Exception>success("abc")
                .mapSuccess(String::length)
                .mapSuccess(numberToStringFunc);
        Result<Integer, String> failureResult = Result.<Integer, String>failure("abc")
                .mapSuccess(num -> num * 0);

        assertThat(successResult).isEqualTo(Result.success("3"));
        assertThat(failureResult).isEqualTo(Result.failure("abc"));
    }

    @Test
    public void testMapFailure() {
        Result<Void, Integer> failureResult = Result.<Void, String>failure("abc")
                .mapFailure(Object::toString)
                .mapFailure(String::length);

        Result<String, Integer> successResult = Result.<String, Integer>success("abc")
                .mapFailure(num -> num * 0);

        assertThat(failureResult).isEqualTo(Result.failure(3));
        assertThat(successResult).isEqualTo(Result.success("abc"));
    }

    @Test
    public void testMap() {
        Integer successResult = Result.success("abc")
                .map(s -> 3, o -> 99);
        Integer failureResult = Result.failure("abc")
                .map(s -> 3, o -> 99);

        assertThat(successResult).isEqualTo(3);
        assertThat(failureResult).isEqualTo(99);
    }

    @Test
    public void testFlatMap_whenFailure() {
        RuntimeException error = new RuntimeException("oops");
        Result<Integer, Exception> result = Result.<String, Exception>failure(error)
                .flatMap(s -> Result.success(3));

        assertThat(result).isEqualTo(Result.failure(error));
    }

    @Test
    public void testFlatMap_whenSuccess() {
        Result<String, Exception> result = Result.<String, Exception>success("YES")
                .flatMap(n -> Result.success(3))
                .flatMap(n -> Result.success(n * 2))
                .flatMap()
                .flatMap(numberToStringResultFunc);

        assertThat(result).isEqualTo(Result.success("6"));
    }

    @Test
    public void fold(){

    }
}