package Commands;

import Annotation.Context;
import Annotation.Inject;

import java.util.Map;
import java.util.Stack;

public class Define implements Command {
    @Inject(arg = Context.MAP)
    Map<String, Double> hashMap;

    @Inject(arg = Context.ARGUMENTS)
    String[] arguments;

    @Override
    public void run() {
        hashMap.put(arguments[1], Double.valueOf(arguments[2]));
    }
}
