package Commands;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PopTest {
    private Pop pop = new Pop();
    private final Stack<Double> stack = new Stack<>();
    private final Map<String, Double> map = new HashMap<>();
    private String[] arguments;

    private static Stream<Arguments> CreateTestedArguments(){
        return Stream.of(Arguments.of(10.0),
                Arguments.of(Double.MAX_VALUE),
                Arguments.of(Double.MIN_VALUE),
                Arguments.of(-10.0));
    }

    @ParameterizedTest
    @MethodSource("CreateTestedArguments")
    void popTest(Double value){
        stack.push(value);
        pop = (Pop) Command.create(pop.getClass(), stack, map, arguments);
        assertEquals(stack.pop(), value);
    }
}
