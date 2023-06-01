package Tasks.Taskers.Producers;

import Tasks.Task.SimpleTask;
import Tasks.Task.TaskInterface;
import Tasks.TaskStorage;

final public class SimpleTaskProducer extends AbstractProducer implements Runnable {
    public SimpleTaskProducer(TaskStorage storage, Integer sleepTime) {
        super(storage, sleepTime, "SimpleTaskProducer [" + GetProducerCount() + "]");
    }

    public TaskInterface ProduceNewTask() {
        return new SimpleTask(20 * GetProducerCount());
    }
}
