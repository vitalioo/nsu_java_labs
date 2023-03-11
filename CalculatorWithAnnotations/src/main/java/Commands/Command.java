package Commands;

import org.apache.log4j.Logger;

public interface Command {
    Logger logger = Logger.getLogger(Command.class.getName());

    public void run();
}
