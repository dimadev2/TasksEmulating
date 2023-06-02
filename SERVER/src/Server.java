import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

import Tasks.Manager;

public class Server {
    ServerSocket Server;
    Socket Console;
    PrintStream Out;
    BufferedReader In;

    Manager manager;

    private enum CODES {
        YES_ANSWER, NO_ANSWER,
        END_CODE
    }

    final String EXIT_COMMAND = "EXIT";

    public Server(int port) {
        try {
            Server = new ServerSocket(port);
            Console = Server.accept();
            Out = new PrintStream(Console.getOutputStream());
            In = new BufferedReader(new InputStreamReader(Console.getInputStream()));
        }
        catch (IOException e) {
            System.out.println("IOException in Server() constructor");
        }

        System.out.println("Connected successfully");

        int capacity;
        try {
            capacity = Integer.parseInt(In.readLine());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        manager = new Manager(capacity, System.out);

        RunSession();
    }

    private void RunSession() {
        String inputLine;
        Class[] parameterTypes = new Class[] {String[].class};
        try {
            do {
                try {
                    inputLine = In.readLine();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                String[] args = inputLine.split(" ");
                String metName = args[0];

                if (metName.equals("help")) {
                    Out.println(CODES.YES_ANSWER.name());
                    Out.flush();
                    Help();
                }
                else {
                    Out.println(CODES.NO_ANSWER);
                    Out.flush();
                    Method met = manager.getClass().getDeclaredMethod(metName, parameterTypes);
                    met.invoke(manager, (Object)args);
                }
//                System.out.println("Storage size: " + manager.GetStorageSize());
//                System.out.println(inputLine);
            } while (!(inputLine = In.readLine()).equals(EXIT_COMMAND));

            manager.Reset(null);
        }
        catch (IOException e) {
            System.out.println("IOException in RunSession() Server method");
        } catch (NoSuchMethodException e) {
            Out.println("No such method");
            Out.flush();
        } catch (InvocationTargetException e) {
            Out.println("ERROR: InvocationTargetException");
            Out.flush();
        } catch (IllegalAccessException e) {
            Out.println("ERROR: IllegalAccessException");
            Out.flush();
        }
    }

    private void Help() {
        Out.println("\t- Available commands:");
        Out.println("\t- AddConsumer <delay in milisecs>");
        Out.println("\t- RemoveConsumer <consumer id> - id written in [x] in the terminal");
        Out.println("\t- AddProducer <producer name> <delay in milisecs>");
        Out.println("\t- RemoveProducer <producer id>");
        Out.println(CODES.END_CODE.name());
        Out.flush();
    }
}
