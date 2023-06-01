import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import Tasks.Manager;

public class Server {
    ServerSocket Server;
    Socket Console;
    PrintStream Out;
    BufferedReader In;

    Manager manager;

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
        Scanner in = new Scanner(In);
        capacity = in.nextInt();
        manager = new Manager(capacity, System.out);

        RunSession();
    }

    private void RunSession() {
        String inputLine;
        Class[] parameterTypes = new Class[] {String[].class};
        try {
            while (!(inputLine = In.readLine()).equals(EXIT_COMMAND)) {
                String[] args = inputLine.split(" ");
                String method = args[0];

                Manager.class.getDeclaredMethod(method, parameterTypes).invoke(manager, args);
//                System.out.println(inputLine);
            }
        }
        catch (IOException e) {
            System.out.println("IOException in RunSession() Server method");
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
