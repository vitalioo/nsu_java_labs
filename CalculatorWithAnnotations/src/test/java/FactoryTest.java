import Commands.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class FactoryTest {
    private String[] arguments;

    private static Stream<Arguments> createTestedArguments() {
        return Stream.of(Arguments.of("DEFINE a 5", Define.class),
                Arguments.of("PUSH 20", Push.class),
                Arguments.of("-", Subtraction.class),
                Arguments.of("+", Addition.class),
                Arguments.of("SQRT", SquareRoot.class));
    }

    @ParameterizedTest
    @MethodSource("createTestedArguments")
    void factoryTest(String line, Class<? extends Command> commandClass) {
        arguments = line.split(" ");
        Command command = Factory.createCommand(arguments);
        if (command == null) {
            throw new AssertionError();
        }
        Assertions.assertEquals(command.getClass(), commandClass);
    }
}
