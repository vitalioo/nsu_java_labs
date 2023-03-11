package Commands;

import Annotation.Context;
import Annotation.Inject;

import java.util.Stack;

import static java.lang.Math.sqrt;

public class SquareRoot implements Command {
    @Inject(arg = Context.STACK)
    private Stack<Double> stack;

    @Override
    public void run() {
        if (stack.size() < 1) {
            logger.warn("No arguments for square root");
        } else {
            stack.push(sqrt(stack.pop()));
        }
    }
}
