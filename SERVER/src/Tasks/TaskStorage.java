package Tasks;

import Tasks.Task.TaskInterface;

import java.util.Stack;

final public class TaskStorage {
    private Stack<TaskInterface> TaskStack = new Stack<>();
    private final int capacity;
    public TaskStorage(int cap) {
        capacity = cap;
    }
    synchronized public TaskInterface getTask() throws InterruptedException {
        if (TaskStack.size() == 0) {
            wait();
        }
        notify();
        return TaskStack.pop();
    }
    synchronized public void putTask(TaskInterface task) throws InterruptedException {
        if (TaskStack.size() == capacity) {
            wait();
        }
        TaskStack.push(task);
        notify();
    }
}
