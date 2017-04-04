import java.io.*;
import java.net.Socket;
import java.util.Scanner;

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
        int PORTNUM;
        if(args.length > 0) {
            PORTNUM = Integer.parseInt(args[0]);
        } else {
            PORTNUM = 4001;
        }
        System.out.println("Test Client Awake");
        Socket myConnection = openSocket("localhost", PORTNUM);
        if(myConnection != null) { //TODO tidy
            System.out.println("Connection established");
            DataInputStream ins = openInputStream(myConnection);
            PrintStream outs = openOutputStream(myConnection);
            if(ins != null && outs != null) {  //TODO tidy
                System.out.println("I/O streams active.");

                // ChatClientChannel threads
                ChatClientChannel inStreamer = new ChatClientChannel(ins);
                ChatClientChannel outStreamer = new ChatClientChannel(outs);
                inStreamer.start();
                outStreamer.start();

                //TODO implement connection closing
                //System.out.println("Closing connection...");
                //closeConnection(myConnection, ins, outs);
            } else {
                System.out.println("Could not activate I/O streams.");
            }
        } else {
            System.out.println("Could not connection to server");
        }
    }
}
