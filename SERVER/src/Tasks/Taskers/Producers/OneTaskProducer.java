package Tasks.Taskers.Producers;

import Tasks.Task.SimpleTask;
import Tasks.Task.TaskInterface;
import Tasks.TaskStorage;

public class OneTaskProducer extends AbstractProducer {
    public OneTaskProducer(TaskStorage storage, Integer sleepTime) {
        super(storage, sleepTime, "OneTaskProducer [" + (GetProducerCount() + 1) + "]");
    }

    public TaskInterface ProduceNewTask() {
        return new SimpleTask(1);
    }
}
