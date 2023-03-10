package Commands;

import Annotation.Context;
import Annotation.Inject;

import java.util.Stack;

public class Pop implements Command {
    @Inject(arg = Context.STACK)
    private Stack<Double> stack;

    @Override
    public void run() {
        if (stack.size() < 1) {
            logger.error("Not enough arguments for pop");
        } else {
            stack.pop();
        }
    }
}
