package Commands;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Stream;

public class PrintTest {
    private Print print = new Print();
    private final Stack<Double> stack = new Stack<>();
    private final Map<String, Double> map = new HashMap<>();
    private String[] arguments;

    private static Stream<Arguments> CreateTestedArguments() {
        return Stream.of(Arguments.of(10.0),
                Arguments.of(Double.MAX_VALUE),
                Arguments.of(Double.MIN_VALUE));
    }

    @ParameterizedTest
    @MethodSource("CreateTestedArguments")
    void printTest(Double argument) {
        stack.push(argument);
        print = (Print) Command.create(print.getClass(), stack, map, arguments);
        ByteArrayOutputStream line = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(line);
        System.setOut(out);
        print.run();

        String testArgument = argument.toString() + "\r\n";
        Assertions.assertEquals(line.toString(), testArgument);
    }
}
