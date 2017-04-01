import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

import static java.lang.Thread.sleep;

public class TestClient {

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
        System.out.println("Test Client Awake");
        Socket myConnection = openSocket("localhost", 3456);
        if(myConnection != null) { //TODO tidy
            System.out.println("Connection established");
            DataInputStream ins = openInputStream(myConnection);
            PrintStream outs = openOutputStream(myConnection);
            if(ins != null && outs != null) {  //TODO tidy
                System.out.println("I/O streams active.");
//                outs.append("Hello server :)");
//                System.out.println("Sent message to server");
                sleep(10000);
//                System.out.println("Server says:");
//                System.out.println("> "+ins.readUTF());
                System.out.println("Closing connection...");
                closeConnection(myConnection, ins, outs);
            } else {
                System.out.println("Could not activate I/O streams.");
            }
        } else {
            System.out.println("Could not connection to server");
        }
    }
}
