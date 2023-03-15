package Commands;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Stream;

public class PushTest {
    private Push push = new Push();
    private final Stack<Double> stack = new Stack<>();
    private final Map<String, Double> map = new HashMap<>();
    private String[] arguments;

    public static Stream<Arguments> CreateTestedArguments() {
        return Stream.of(Arguments.of("PUSH 10"),
                Arguments.of("PUSH 5"),
                Arguments.of("PUSH -000"));
    }

    @ParameterizedTest
    @MethodSource("CreateTestedArguments")
    void pushTest(String line) {
        arguments = line.split(" ");
        push = (Push) Command.create(push.getClass(), stack, map, arguments);
        push.run();
        Assertions.assertEquals(stack.peek(), Double.valueOf(arguments[1]));
    }
}
