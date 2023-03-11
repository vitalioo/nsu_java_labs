package Commands;

import Annotation.Context;
import Annotation.Inject;

import java.util.Stack;

public class Subtraction implements Command {
    @Inject(arg = Context.STACK)
    private Stack<Double> stack;

    @Override
    public void run() {
        if (stack.size() < 2) {
            logger.warn("Not enough arguments for -");
        } else {
            stack.push(stack.pop() - stack.pop());
        }
    }
}
