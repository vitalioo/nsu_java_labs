import Commands.*;
import org.apache.log4j.Logger;

import java.io.*;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        if (args.length == 0) {
            logger.error("No input file");
            return;
        }
        String line;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(args[0]))) {
            while (true) {
                if ((line = bufferedReader.readLine()) == null) {
                    break;
                } else {
                    logger.info("Parse input arguments");
                    Command command = Factory.createCommand(line.split(" "));
                    if (command != null) {
                        logger.info("Created and run " + command.getClass());
                        command.run();
                    } else {
                        logger.warn("Not existed command");
                    }
                }
            }
        } catch (IOException ex) {
            logger.error(ex);
            throw new IllegalStateException(ex);
        }
    }
}
