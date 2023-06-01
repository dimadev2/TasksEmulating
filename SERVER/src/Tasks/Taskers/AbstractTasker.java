package Tasks.Taskers;

import Tasks.Task.TaskInterface;
import Tasks.TaskStorage;

import java.io.PrintStream;

abstract public class AbstractTasker implements Runnable {
    public Thread thread;
    protected TaskStorage Storage;
    protected TaskInterface CurrentTask;
    final protected int SleepTime;

    protected PrintStream Out;

    protected AbstractTasker(TaskStorage storage, int sleepTime) {
        Storage = storage;
        SleepTime = sleepTime;
    }

    public void SetPrintStream(PrintStream ps) {
        Out = ps;
    }
}
