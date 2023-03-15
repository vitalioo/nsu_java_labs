package Commands;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Stream;

public class SubtractionTest {
    private Subtraction subtraction = new Subtraction();
    private final Stack<Double> stack = new Stack<>();
    private final Map<String, Double> map = new HashMap<>();
    private String[] arguments;

    private static Stream<Arguments> CreateTestedArguments() {
        return Stream.of(Arguments.of(10.0, -10.0),
                Arguments.of(1001.0, 1001.0),
                Arguments.of(Double.MAX_VALUE, Double.MAX_VALUE),
                Arguments.of(0.0, -0.0));
    }

    @ParameterizedTest
    @MethodSource("CreateTestedArguments")
    void additionTest(Double minuend, Double subtrahend) {
        stack.push(subtrahend);
        stack.push(minuend);
        subtraction = (Subtraction) Command.create(subtraction.getClass(), stack, map, arguments);
        subtraction.run();
        Assertions.assertEquals(stack.pop(), minuend - subtrahend);
    }

}
