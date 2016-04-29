import com.djulia.Either;
import org.junit.Test;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;

public class EitherTest {

    @org.junit.Test
    public void testMapLeft() throws Exception {
        Either<Integer, Void> mapped = Either.<String, Void>left("abc").mapLeft(String::length);
        assertThat(mapped).isEqualTo(Either.left(3));
    }

    @org.junit.Test
    public void testMapLeft_mappingToSubtype() throws Exception {
        Either<Number, Void> mapped = Either.<Integer, Void>left(3).mapLeft(num -> num/2.0);
        assertThat(mapped).isEqualTo(Either.left(1.5));
    }

    @Test
    public void testMapLeft_whenIsRightValue(){
        Either<Integer, String> mapped = Either.<Integer, String>right("abc")
                .mapLeft(num -> num*0);
        assertThat(mapped).isEqualTo(Either.right("abc"));
    }

    @Test
    public void testMapRight(){
        Either<Void, Integer> mapped = Either.<Void, String>right("abc")
                .mapRight(String::length);
        assertThat(mapped).isEqualTo(Either.right(3));
    }
    @Test
    public void testMapRight_whenIsLeftValue(){
        Either<String, Integer> mapped = Either.<String, Integer>left("abc")
                .mapRight(num -> num*0);
        assertThat(mapped).isEqualTo(Either.left("abc"));
    }

    @Test
    public void flatMapLeft(){
        Either<Integer, Object> mapped = Either.left("abc")
                .flatMapLeft(s -> Either.left(s.length()));

        assertThat(mapped).isEqualTo(Either.left(3));
    }
    @Test
    public void flatMapLeft_whenRight(){
        Either<Integer, Object> mapped = Either.<String, Object>right("someobject")
                .flatMapLeft(s -> Either.left(s.length()));

        assertThat(mapped).isEqualTo(Either.right("someobject"));
    }

    @Test
    public void testMap_whenLeft(){
        Integer result = Either.left("abc")
                .map(s -> 3, o -> 99);

        assertThat(result).isEqualTo(3);

    }

    @Test
    public void testMap_whenRight(){
        Integer result = Either.right("abc")
                .map(s -> 3, o -> 99);

        assertThat(result).isEqualTo(99);
    }

}