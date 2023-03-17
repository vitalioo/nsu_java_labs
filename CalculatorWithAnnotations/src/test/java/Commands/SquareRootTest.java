package Commands;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.lang.Math;

import java.util.stream.Stream;

public class SquareRootTest {
    private SquareRoot sqrt = new SquareRoot();
    private final Stack<Double> stack = new Stack<>();
    private final Map<String, Double> map = new HashMap<>();
    private String[] arguments;

    public static Stream<Arguments> createTestedArguments() {
        return Stream.of(Arguments.of(0.0),
                Arguments.of(-0.0),
                Arguments.of(1.0),
                Arguments.of(9801.0));
    }

    @ParameterizedTest
    @MethodSource("createTestedArguments")
    void pushTest(Double argument) {
        stack.push(argument);
        sqrt = (SquareRoot) Command.create(sqrt.getClass(), stack, map, arguments);
        sqrt.run();
        Assertions.assertEquals(stack.peek(), Math.sqrt(argument));
    }
}
