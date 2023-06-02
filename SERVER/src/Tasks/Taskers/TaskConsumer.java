package Tasks.Taskers;

import Tasks.TaskStorage;

final public class TaskConsumer extends AbstractTasker implements Runnable {
    static private int ConsumerCount;

    static {
        ConsumerCount = 0;
    }

    static public int getConsumerCount() {
        return ConsumerCount;
    }

    public TaskConsumer(TaskStorage storage, int sleepTime) {
        super(storage, sleepTime);
        ConsumerCount++;
        thread = new Thread(this, "TaskConsumer [" + ConsumerCount + "]");
        thread.start();
    }

    public void run() {
        try {
            while (true) {
                if (!thread.isInterrupted()) {
                    GetTask();
                    ExecuteTask();
                } else {
                    return;
                }

                Thread.sleep(SleepTime);
            }
        }
        catch (InterruptedException ignored) {

        }
    }

    private void GetTask() throws InterruptedException {
        Out.println(thread.getName() + " waiting for a task");
        CurrentTask = Storage.getTask();
        Out.println(thread.getName() + " got new task");
//        Out.println("Storage size: " + Storage.GetSize());
    }

    private void ExecuteTask() {
//        System.out.println(thread.getName() + " executing task");
        CurrentTask.execute();
        Out.println(thread.getName() + " executed task");
    }
}
