import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {

    static int PORTNUM = 3456; //port


    public static ServerSocket listen(int port) {
        // Creating the server socket
        ServerSocket MyService;
        try {
            MyService = new ServerSocket(port);
            System.out.println("Server socket listening...");
            return MyService;
        } catch (IOException e) {
            System.out.println(e);
            return null;
        }
    }
 //leaving this out! rather use in main
    private static Socket createSocket(ServerSocket service) {
        // server creates a client socket to communicate with the client
        Socket clientSocket = null;
        try {
            clientSocket = service.accept(); // Not sure about this...
            return clientSocket;
        }
        catch ( IOException e ) {
            System.out.println(e);
            return null;
        }

    }

    public static void closeSocket(Socket clientSocket, DataInputStream input, PrintStream output) {
        // close the input and output streams and the socket
        try {
            output.close ();
            input.close ();
            clientSocket.close ();
        }
        catch ( IOException e ) {
            System.out.println(e);
        }
    }

    public static DataInputStream openInputStream(Socket clientSocket) {
        // DataInputStream is used to receive inputs from the client
        DataInputStream input;
        try {
            input = new DataInputStream(clientSocket.getInputStream());
            return input;
        }
        catch ( IOException e ) {
            System.out.println(e);
            return null;
        }
    }

    public static PrintStream openOutputStream(Socket clientSocket) {
        // PrintStream used to send data to client
        PrintStream output;
        try {
            output = new PrintStream(clientSocket.getOutputStream());
            return output;
        }
        catch ( IOException e ) {
            System.out.println(e);
            return null;
        }
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Server awake. To stop press CNTRL C");
        ServerSocket listener = listen(PORTNUM);
        if(listener != null) { //TODO tidy
            System.out.println("Listening on port...");
            Socket conn1 = createSocket(listener);
            if(conn1 != null) { //TODO tidy
                System.out.println("New connection!");
                DataInputStream in1 = openInputStream(conn1);
                PrintStream out1 = openOutputStream(conn1);
                if(in1 != null && out1 != null) { //TODO tid
                  while(true){
                    System.out.println("Client says:");
                    System.out.println("> "+in1.readLine());
                    System.out.println("Sent \"ok\" to client");
                      out1.println("ok client :P");
                    //closeSocket(conn1, in1, out1);
                  //  System.out.println("Connection to client was closed.");
                }
                } else {
                    System.out.println("Could not open connections");
                }
            } else {
                System.out.println("no connection");
            }
        } else {
            System.out.println("Could not listen on port!");
        }
    }









}
