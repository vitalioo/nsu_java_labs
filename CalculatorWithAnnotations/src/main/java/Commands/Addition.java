package Commands;

import Annotation.*;

import java.util.Stack;

public class Addition implements Command {
    @Inject(arg = Context.STACK)
    private Stack<Double> stack;

    @Override
    public void run() {
        if (stack.size() < 2) {
            logger.warn("Not enough arguments for +");
        } else {
            stack.push(stack.pop() + stack.pop());
        }
    }
}
