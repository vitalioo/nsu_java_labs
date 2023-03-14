package Commands;


import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class PrintTest {
    private Print print = new Print();
    private final Stack<Double> stack = new Stack<>();
    private final Map<String, Double> map = new HashMap<>();
    private String[] arguments;

    private static Stream<Arguments> CreateTestedArguments(){
        return Stream.of(Arguments.of(10.0),
                Arguments.of(Double.MAX_VALUE),
                Arguments.of(Double.MIN_VALUE));
    }

    @ParameterizedTest
    @MethodSource("CreateTestedArguments")
    void popTest(Double value){
        /*stack.push(value);
        print = (Print) Command.create(print.getClass(), stack, map, arguments);
        print.run();*/
    }
}
