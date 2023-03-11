import Commands.*;

import java.io.*;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("No input file");
            return;
        }
        String line;
        Factory factory = new Factory();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(args[0]))) {
            while (true) {
                if ((line = bufferedReader.readLine()) == null) {
                    break;
                } else {
                    Command command = factory.createCommand(line.split(" "));
                    if (command != null) {
                        command.run();
                    } else {
                        System.out.println("Not existed command");
                    }
                }
            }
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }
    }
}
