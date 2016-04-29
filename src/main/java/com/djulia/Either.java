package com.djulia;
import java.util.function.Function;


import java.util.Optional;
import java.util.function.Function;

public class Either<L, R> {
    private final Optional<L> left;
    private final Optional<R> right;

    private Either(Optional<L> left, Optional<R> right) {
        this.left = left;
        this.right = right;
    }

    public static <L, R> Either<L, R> left(L left) {
        return new Either<>(Optional.of(left), Optional.<R>empty());
    }

    public static <L, R> Either<L, R> right(R right) {
        return new Either<>(Optional.empty(), Optional.of(right));
    }

    public boolean isLeft(){
        return left.isPresent();
    }

    public boolean isRight(){
        return right.isPresent();
    }

    public <T> Either<T, R> mapLeft(Function<L, T> mapper) {
        Optional<T> left = this.left.map(mapper);
        return new Either<>(left, right);
    }
    public <T> Either<L, T> mapRight(Function<R, T> mapper) {
        Optional<T> right = this.right.map(mapper);
        return new Either<>(left, right);

    }

    @Override
    public String toString() {
        return "Either{" +
                "left=" + left +
                ", right=" + right +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Either<?, ?> either = (Either<?, ?>) o;

        if (left != null ? !left.equals(either.left) : either.left != null) return false;
        return right != null ? right.equals(either.right) : either.right == null;

    }

    @Override
    public int hashCode() {
        int result = left != null ? left.hashCode() : 0;
        result = 31 * result + (right != null ? right.hashCode() : 0);
        return result;
    }


    public <T> Either<T, R> flatMapLeft(Function<L, Either<T, R>> function) {
        if(left.isPresent()){
            return function.apply(left.get());
        }
        return Either.right(right.get()
        );
    }

    public <T> T map(Function<L, T> leftTransform, Function<R, T> rightTransform) {
        if(left.isPresent()){
            return leftTransform.apply(left.get());
        }
        return rightTransform.apply(right.get());
    }
}
