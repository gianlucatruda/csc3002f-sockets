import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

import static java.lang.Thread.sleep;

public class ChatClient {

    private static Socket openSocket(String name, int port) {
        Socket MyClient;
        try {
            MyClient = new Socket(name, port);
            return MyClient;
        }
        catch (IOException e) {
            System.out.println(e);
            return null;
        }
    }

    private static DataInputStream openInputStream(Socket myClient) {
        DataInputStream input;
        try {
            input = new DataInputStream(myClient.getInputStream());
            return input;
        }
        catch (IOException e) {
            System.out.println(e);
            return null;
        }
    }

    private static PrintStream openOutputStream(Socket myClient) {
        PrintStream output;
        try {
            output = new PrintStream(myClient.getOutputStream());
            return output;
        }
        catch (IOException e) {
            System.out.println(e);
            return null;
        }
    }

    private static void closeConnection(Socket myClient, DataInputStream input, PrintStream output) {
        try {
            output.close();
            input.close();
            myClient.close();
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {

    }

}
