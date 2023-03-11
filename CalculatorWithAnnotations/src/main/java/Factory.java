import Commands.Command;

import Annotation.*;

import java.io.*;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Stack;

public class Factory {
    private final Stack<Double> stack = new Stack<>();
    private final Map<String, Double> map = new HashMap<>();
    private static final Properties properties;

    static {
        properties = new Properties();
        try (BufferedReader file = new BufferedReader(new FileReader("src/main/java/config.properties"))) {
            properties.load(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Command createCommand(String[] arguments) {
        Command command = null;
        try {
            Class factoryClass = Class.forName(properties.getProperty(arguments[0]));
            if (factoryClass.newInstance() instanceof Command) {
                command = (Command) factoryClass.newInstance();
                Field[] declaredFields = factoryClass.getDeclaredFields();

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
            throw new IllegalStateException();
        }
        return command;
    }
}
