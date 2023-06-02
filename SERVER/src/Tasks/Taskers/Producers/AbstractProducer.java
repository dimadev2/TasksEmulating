package Tasks.Taskers.Producers;

import Tasks.Taskers.AbstractTasker;
import Tasks.Task.TaskInterface;
import Tasks.TaskStorage;

abstract public class AbstractProducer extends AbstractTasker implements Runnable {
    static private int ProducerCount;

    static {
        ProducerCount = 0;
    }

    static public int GetProducerCount() {
        return ProducerCount;
    }

    protected AbstractProducer(TaskStorage storage, int sleepTime, String threadName) {
        super(storage, sleepTime);
        ProducerCount++;
        thread = new Thread(this, threadName);
        thread.start();
    }

    abstract protected TaskInterface ProduceNewTask();

    public void run() {
        try {
            while (true) {
                if (!thread.isInterrupted()) {
                    CurrentTask = ProduceNewTask();
                    PutTask();
                } else {
                    return;
                }

                Thread.sleep(SleepTime);
            }
        }
        catch (InterruptedException ignored) {

        }
    }

    private void PutTask() throws InterruptedException {
        Out.println(thread.getName() + " waiting to put new task");
        Storage.putTask(CurrentTask);
        Out.println(thread.getName() + " put new task");
    }
}
