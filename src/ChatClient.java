import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

public class ChatClient {

    private Socket openSocket(String name, int port) {
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

    private DataInputStream openInputStream(Socket myClient) {
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

    private PrintStream openOutputStream(Socket myClient) {
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

    private void closeConnection(Socket myClient, DataInputStream input, PrintStream output) {
        try {
            output.close();
            input.close();
            myClient.close();
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        System.out.println("Test Client Awake");
        
    }

}
