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

    private enum CODES {
        YES_ANSWER, NO_ANSWER,
        END_CODE
    }

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
            System.out.print("~#: ");
            buffer = in.nextLine();
            Out.println(buffer);
            String code;
            try {
                code = In.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (CODES.valueOf(code) == CODES.YES_ANSWER) {
                RecvAnswer();
            }
        } while (!buffer.equals(EXIT_COMMAND));
    }

    private void RecvAnswer() {
        String msg;
        try {
            msg = In.readLine();
            while (!msg.equals(CODES.END_CODE.name())) {
                System.out.println(msg);
                msg = In.readLine();
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
