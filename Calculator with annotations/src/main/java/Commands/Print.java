package Commands;

import Annotation.Context;
import Annotation.Inject;

import java.util.Stack;

public class Print implements Command {
    @Inject(arg = Context.STACK)
    Stack<Double> stack;

    @Override
    public void run() {
        if (stack.size() == 0) {
            System.out.println("Nothing to print");
        } else {
            System.out.println(stack.peek());
        }
    }
}
