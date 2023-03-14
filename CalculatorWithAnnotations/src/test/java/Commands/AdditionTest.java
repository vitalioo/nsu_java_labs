package Commands;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class AdditionTest {
    private Addition addition = new Addition();
    private final Stack<Double> stack = new Stack<>();
    private final Map<String, Double> map = new HashMap<>();
    private String[] arguments;

    private static Stream<Arguments> CreateTestedArguments(){
        return Stream.of(Arguments.of(5.0, 6.0),
                Arguments.of(-1001.0, 1001.0),
                Arguments.of(Double.MAX_VALUE, Double.MIN_VALUE),
                Arguments.of(0.0, -0.0));
    }

    @ParameterizedTest
    @MethodSource("CreateTestedArguments")
    void additionTest(Double firstTerm, Double secondTerm) {
        stack.push(firstTerm);
        stack.push(secondTerm);
        addition = (Addition) Command.create(addition.getClass(), stack, map, arguments);
        addition.run();
        assertEquals(stack.pop(), firstTerm + secondTerm);
    }
}
