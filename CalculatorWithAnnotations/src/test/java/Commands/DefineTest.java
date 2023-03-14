package Commands;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DefineTest {
    private Define define = new Define();
    private final Stack<Double> stack = new Stack<>();
    private final Map<String, Double> map = new HashMap<>();
    private String[] arguments;

    private static Stream<Arguments> CreateTestedArguments(){
        return Stream.of(Arguments.of(("DEFINE a 5")),
                Arguments.of(("DEFINE b 10")));
    }

    @ParameterizedTest
    @MethodSource("CreateTestedArguments")
    void defineTest(String line) {
        arguments = line.split(" ");
        define = (Define) Command.create(define.getClass(), stack, map, arguments);
        define.run();
        assertEquals(map.get(arguments[1]), Double.valueOf(arguments[2]));
    }
}
