package Tasks;

import Tasks.Taskers.Producers.AbstractProducer;
import Tasks.Taskers.TaskConsumer;

import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class Manager {
    volatile private TaskStorage Storage;
    Map<Integer, TaskConsumer> ConsumersMap = new HashMap<>();
    Map<Integer, AbstractProducer> ProducersMap = new HashMap<>();

    private PrintStream CommonOut;

    public Manager(int storageCapacity, PrintStream commonOut) {
        Storage = new TaskStorage(storageCapacity);
        CommonOut = commonOut;
    }

    public void AddConsumer(String[] args) {
        int sleepTime = Integer.parseInt(args[1]);
        TaskConsumer newConsumer = new TaskConsumer(Storage, sleepTime);
        newConsumer.SetPrintStream(CommonOut);
        ConsumersMap.put(TaskConsumer.getConsumerCount(), newConsumer);
    }

    public void RemoveConsumer(String[] args) {
        int id = Integer.parseInt(args[1]);
        if (ConsumersMap.containsKey(id)) {
            ConsumersMap.get(id).thread.interrupt();
            ConsumersMap.remove(id);
        }
    }

    public void AddProducer(String[] args) {
        String className = args[1];
        int sleepTime = Integer.parseInt(args[2]);
        try {
            Class prodClass = Class.forName("Tasks.Taskers.Producers." + className);
            Constructor prodConstructor = prodClass.getConstructor(TaskStorage.class, Integer.class);
            AbstractProducer newProducer = (AbstractProducer) prodConstructor.newInstance(Storage, (Integer)sleepTime);
            newProducer.SetPrintStream(CommonOut);
            ProducersMap.put(AbstractProducer.GetProducerCount(), newProducer);

        } catch (InstantiationException | InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
            CommonOut.println("Can't init class");
        } catch (ClassNotFoundException e) {
            CommonOut.println("Class " + className + " not found");
        }
    }

    public void RemoveProducer(String[] args) {
        int id = Integer.parseInt(args[1]);
        if (ProducersMap.containsKey(id)) {
            ProducersMap.get(id).thread.interrupt();
            ProducersMap.remove(id);
        }
    }

    public void Reset(String[] args) {
        for (Integer key: ProducersMap.keySet()) {
            ProducersMap.get(key).thread.interrupt();
        }
        for (Integer key: ConsumersMap.keySet()) {
            ConsumersMap.get(key).thread.interrupt();
        }
        ProducersMap = new HashMap<>();
        ConsumersMap = new HashMap<>();
    }

    public int GetStorageSize() {
        return Storage.GetSize();
    }
}
