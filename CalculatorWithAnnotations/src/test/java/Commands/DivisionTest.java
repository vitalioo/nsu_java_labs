package Commands;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Stream;

public class DivisionTest {
    private Division division = new Division();

    private final Stack<Double> stack = new Stack<>();
    private final Map<String, Double> map = new HashMap<>();
    private String[] arguments;

    private static Stream<Arguments> createTestedArguments() {
        return Stream.of(Arguments.of(5.0, 1.0),
                Arguments.of(1000000.0, 2.0),
                Arguments.of(10.0, -10.0));
    }

    @ParameterizedTest
    @MethodSource("createTestedArguments")
    void divisionTest(Double dividend, Double divider) {
        stack.push(divider);
        stack.push(dividend);
        division = (Division) Command.create(division.getClass(), stack, map, arguments);
        division.run();
        Assertions.assertEquals(stack.pop(), dividend / divider);
    }
}
