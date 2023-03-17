import Commands.Command;

import org.apache.log4j.Logger;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
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
        if (arguments.length > 3) {
            logger.warn("Invalid input arguments");
            return null;
        }

        Command command = null;
        try {
            Class factoryClass = Class.forName(properties.getProperty(arguments[0]));
            if (factoryClass.getDeclaredConstructor().newInstance() instanceof Command) {
                command = Command.create(factoryClass, stack, map, arguments);
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NullPointerException ex) {
            logger.warn(ex);
            throw new IllegalStateException();
        } catch (InvocationTargetException | NoSuchMethodException ex) {
            throw new RuntimeException(ex);
        }
        return command;
    }
}
