package com.djulia;

import java.util.Optional;
import java.util.function.Function;

public class Result<S, F> {
    private final Optional<S> success;
    private final Optional<F> failure;

    private Result(Optional<S> success, Optional<F> failure) {
        this.success = success;
        this.failure = failure;
    }

    public static <S, F> Result<S, F> success(S success) {
        return new Result<>(Optional.of(success), Optional.<F>empty());
    }

    public static <S, F> Result<S, F> failure(F failure) {
        return new Result<>(Optional.empty(), Optional.of(failure));
    }

    public boolean isSuccess(){
        return success.isPresent();
    }

    public boolean isFailure(){
        return failure.isPresent();
    }

    public <T> Result<T, F> mapSuccess(Function<? super S, T> mapper) {
        Optional<T> left = this.success.map(mapper);
        return new Result<>(left, failure);
    }
    public <T> Result<S, T> mapFailure(Function<? super F, T> mapper) {
        Optional<T> failure = this.failure.map(mapper);
        return new Result<>(success, failure);

    }

    public <T> T map(Function<? super S, T> successTransform, Function<F, T> failureTransform) {
        if(isSuccess()){
            return successTransform.apply(success.get());
        }
        return failureTransform.apply(failure.get());
    }

    public <T> Result<T, F> flatMap(Function<? super S, Result<T, F>> successMapper) {
        if(isFailure()){
            return Result.failure(failure.get());
        }

        return successMapper.apply(success.get());
    }

    @Override
    public String toString() {
        return "Result{" +
                "success=" + success +
                ", failure=" + failure +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Result<?, ?> either = (Result<?, ?>) o;

        if (success != null ? !success.equals(either.success) : either.success != null) return false;
        return failure != null ? failure.equals(either.failure) : either.failure == null;

    }

    @Override
    public int hashCode() {
        int result = success != null ? success.hashCode() : 0;
        result = 31 * result + (failure != null ? failure.hashCode() : 0);
        return result;
    }
}
