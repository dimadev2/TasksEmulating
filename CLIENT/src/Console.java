import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Console {
    Socket Server;
    PrintStream Out;
    BufferedReader In;
    final String EXIT_COMMAND = "EXIT";

    public Console(String serverHost, int port) throws IOException {
        try {
            Server = new Socket(serverHost, port);
            Out = new PrintStream(Server.getOutputStream());
            In = new BufferedReader(new InputStreamReader(Server.getInputStream()));
        }
        catch (UnknownHostException e) {
            System.out.println("UnknownHostException in Console() constructor");
        }
        catch (IOException e) {
//            System.out.println("IOException in Console() constructor");
            throw new IOException();
        }

        System.out.println("Connected to server successfully");

        System.out.print("Enter storage capacity: ");
        Scanner in = new Scanner(System.in);
        int capacity = in.nextInt();
        Out.println(capacity);

        RunSession();
    }

    private void RunSession() {
        String buffer;
        Scanner in = new Scanner(System.in);

        do {
            buffer = in.nextLine();
            Out.println(buffer);
        } while (!buffer.equals(EXIT_COMMAND));
    }
}
