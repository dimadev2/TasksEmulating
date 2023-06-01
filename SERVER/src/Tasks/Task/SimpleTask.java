package Tasks.Task;

public class SimpleTask implements TaskInterface {
    private int repeatNum;
    private int result = 0;
    public SimpleTask(int repNum) {
        repeatNum = repNum;
    }
    @Override
    public void execute() {
        for (int i = 0; i < repeatNum; i++) {
            result++;
        }
//        System.out.println("Task " + repeatNum + "\tResult: " + result);
    }
}
