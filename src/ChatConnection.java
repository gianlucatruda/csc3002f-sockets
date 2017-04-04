import javax.print.attribute.standard.MediaSize;
import javax.sound.sampled.Port;
import java.io.*;
import java.lang.reflect.Array;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

public class ChatConnection extends Thread {

    private int PORTNUM = 3456;
    private String NAME = "Alice";
    private String clientName = String.valueOf(Math.random()).substring(2,6);
    private ArrayList<String> unreads;
    private ArrayList<String> messages;
    ChatConnection[] connections;

    DataInputStream inStream;
    PrintStream outStream;

    public ChatConnection(String name, int port, ChatConnection[] conns, ArrayList<String> msgs) {
        PORTNUM = port;
        NAME = name;
        unreads = new ArrayList<String>();
        messages = msgs;
        connections = conns;
    }

    private ServerSocket listen(int port) {
        // Creating the server socket
        ServerSocket MyService;
        try {
            MyService = new ServerSocket(port);
            System.out.println(NAME+" is listening on port "+PORTNUM);
            return MyService;
        } catch (IOException e) {
            System.out.println(e);
            return null;
        }
    }

    private Socket createSocket(ServerSocket service) {
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

    private void closeSocket(Socket clientSocket, DataInputStream input, PrintStream output) {
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

    private DataInputStream openInputStream(Socket clientSocket) {
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

    private PrintStream openOutputStream(Socket clientSocket) {
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

    public ArrayList<String> getUndeliveredMessages() {
        if(unreads.size() > 0) {
            ArrayList<String> out = new ArrayList<>();
            for(String s: unreads) {
                out.add(s);
            }
            unreads.clear();
            return out;
        }
        return new ArrayList<String>();
    }

    public void run() {
        System.out.println("Init server -\t"+NAME+":"+PORTNUM);
        ServerSocket listenSoc = listen(PORTNUM);
        Socket connection = createSocket(listenSoc);
        inStream = openInputStream(connection);
        outStream = openOutputStream(connection);

        if(inStream != null && outStream != null && connection != null) {
            System.out.println("'"+clientName+"' connected to "+NAME);

            // ChatClientChannel threads
            ChatServerChannel inStreamer = new ChatServerChannel(inStream, unreads, connections);
            ChatServerChannel outStreamer = new ChatServerChannel(outStream, unreads, connections);
            inStreamer.start();
            outStreamer.start();

            for(ChatConnection c : connections) {
                if (c != null && c != this) {
                    if(c.outStream != null) {
                        c.outStream.println("'"+clientName+"' connected to "+NAME);
                    }

                }
            }
            
            //TODO implement connection closing
            //closeSocket(connection, inStream, outStream);
            //System.out.println(NAME+": Connection to '"+clientName+"' was closed.");

        } else {
            System.out.println(NAME+": Could not establish a proper connection.");
            System.exit(6);
        }

    }








}

