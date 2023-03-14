package Commands;

import Annotation.Context;
import Annotation.Inject;
import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Stack;

public interface Command {
    Logger logger = Logger.getLogger(Command.class.getName());

    void run();

    static Command create(Class<? extends Command> commandClass,
                          Stack<Double> stack, Map<String, Double> map, String[] arguments) {
        Command command;
        try {
            command = commandClass.newInstance();
            Field[] declaredFields = commandClass.getDeclaredFields();
            logger.debug("Declared fields from class was taken");

            Inject inject;
            for (Field field : declaredFields) {
                inject = field.getDeclaredAnnotation(Inject.class);
                field.setAccessible(true);

                if (inject.arg() == Context.MAP) {
                    field.set(command, map);

                } else if (inject.arg() == Context.STACK) {
                    field.set(command, stack);

                } else if (inject.arg() == Context.ARGUMENTS) {
                    field.set(command, arguments);
                }
            }
        } catch (InstantiationException | IllegalAccessException ex) {
            logger.warn("ex");
            throw new IllegalStateException();
        }

        return command;
    }
}
