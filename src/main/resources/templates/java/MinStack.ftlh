<#import "common.ftlh" as com>
package ${pkg};

<@com.solution_imports/>

<@com.javadoc lines=problem.description?split("\n")/>
public class ${problem.name} {

    /**
     * Pushes the element val onto the stack.
     * @param val element to push onto the stack.
     */
    @NotImplemented
    public void push(int val) {
    }

    /**
     * Removes the element on the top of the stack.
     */
    @NotImplemented
    public void pop() {
    }

    /**
     * Gets the top element of the stack.
     * @return The top element of the stack.
     */
    @NotImplemented
    public int top() {
        return -1;
    }

    /**
     * Gets the minimum element of the stack.
     * @return The minimum element of the stack.
     */
    @NotImplemented
    public int getMin() {
        return -1;
    }

}