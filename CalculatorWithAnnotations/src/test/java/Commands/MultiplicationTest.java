package Commands;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Stream;

public class MultiplicationTest {
    private Multiplication multiplication = new Multiplication();
    private final Stack<Double> stack = new Stack<>();
    private final Map<String, Double> map = new HashMap<>();
    private String[] arguments;

    private static Stream<Arguments> CreateTestedArguments() {
        return Stream.of(Arguments.of(5.0, 5.0),
                Arguments.of(0.0, 1.0),
                Arguments.of(Double.MAX_VALUE, 0.0),
                Arguments.of(-334.5, 1000.0));
    }

    @ParameterizedTest
    @MethodSource("CreateTestedArguments")
    void multiplicationTest(Double firstMultiplier, Double secondMultiplier) {
        stack.push(firstMultiplier);
        stack.push(secondMultiplier);
        multiplication = (Multiplication) Command.create(multiplication.getClass(), stack, map, arguments);
        multiplication.run();
        Assertions.assertEquals(stack.pop(), firstMultiplier * secondMultiplier);
    }
}
