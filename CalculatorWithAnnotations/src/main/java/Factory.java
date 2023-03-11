import Commands.Command;

import Annotation.*;
import org.apache.log4j.Logger;

import java.io.*;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Stack;

public abstract class Factory {
    private static final Logger logger = Logger.getLogger(Factory.class.getName());
    private static final Stack<Double> stack = new Stack<>();
    private static final Map<String, Double> map = new HashMap<>();
    private static final Properties properties;

    static {
        properties = new Properties();
        try (BufferedReader file = new BufferedReader(new FileReader("src/main/java/config.properties"))) {
            properties.load(file);
        } catch (IOException ex) {
            logger.error(ex);
            throw new RuntimeException(ex);
        }
    }

    public static Command createCommand(String[] arguments) {
        Command command = null;
        try {
            Class factoryClass = Class.forName(properties.getProperty(arguments[0]));
            if (factoryClass.newInstance() instanceof Command) {
                command = (Command) factoryClass.newInstance();
                Field[] declaredFields = factoryClass.getDeclaredFields();
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
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            logger.error(ex);
            throw new IllegalStateException();
        }
        return command;
    }
}
