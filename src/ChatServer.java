import com.sun.tools.doclets.formats.html.SourceToHTMLConverter;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {

    static int PORTNUM = 1024;

    public static void main(String[] args) {


        // Creating the server socket
        ServerSocket MyService ; try {
            MyService = new ServerSocket(PORTNUM);
        }
        catch ( IOException e ) {
            System.out.println(e);
        }

        // server creates a client socket to communicate with the client
        Socket clientSocket = null;
        try {
            serviceSocket = MyService.accept();
        }
        catch ( IOException e ) {
            System.out.println(e);
        }


        // DataInputStream is used to receive inputs from the client
        DataInputStream input;
        try {
            input = new DataInputStream(serviceSocket.getInputStream ());
        }
        catch ( IOException e ) {
            System.out.println(e);
        }

        // PrintStream used to send data to client
        PrintStream output;
        try {
            output = new PrintStream(serviceSocket.getOutputStream());
        }
        catch ( IOException e ) {
            System.out.println(e);
        }

        // close the input and output streams and the sockets
        try {
            output.close ();
            input.close ();
            serviceSocket.close ();
            MyService.close ();
        }
        catch ( IOException e ) {
            System.out.println(e);
        }

    }

}
