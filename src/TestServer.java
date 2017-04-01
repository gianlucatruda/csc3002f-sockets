import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TestServer {

    static int PORTNUM = 3456;


    private static ServerSocket listen(int port) {
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

    private static void closeSocket(Socket clientSocket, DataInputStream input, PrintStream output) {
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

    private static DataInputStream openInputStream(Socket clientSocket) {
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

    private static PrintStream openOutputStream(Socket clientSocket) {
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
        System.out.println("Server awake.");
        ServerSocket listener = listen(PORTNUM);
        if(listener != null) { //TODO tidy
            System.out.println("Listening on port...");
            Socket conn1 = createSocket(listener);
            if(conn1 != null) { //TODO tidy
                System.out.println("New connection!");
                DataInputStream in1 = openInputStream(conn1);
                PrintStream out1 = openOutputStream(conn1);
                if(in1 != null && out1 != null) { //TODO tidy

                    BufferedReader br = new BufferedReader(new InputStreamReader(in1));
                    String msg = null;
                    while((msg = br.readLine()) != null) {
                        System.out.println("Client:\t"+msg);
                    }

                    closeSocket(conn1, in1, out1);
                    System.out.println("Connection to client was closed.");
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
