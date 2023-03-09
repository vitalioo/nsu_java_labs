package Commands;

import Annotation.Context;
import Annotation.Inject;

import java.util.Map;
import java.util.Stack;

public class Push implements Command {
    @Inject(arg = Context.STACK)
    Stack<Double> stack;

    @Inject(arg = Context.MAP)
    Map<String, Double> hashMap;

    @Inject(arg = Context.ARGUMENTS)
    String[] arguments;

    @Override
    public void run() {
        if (hashMap.containsKey(arguments[1])) {
            stack.push(hashMap.get(arguments[1]));
        } else {
            stack.push(Double.valueOf(arguments[1]));
        }
    }
}
