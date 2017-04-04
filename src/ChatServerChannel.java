import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ChatServerChannel extends Thread {

    ArrayList<String> msgs;
    DataInputStream dis = null;
    PrintStream ps = null;
    String clientName = "ANON";
    ChatConnection[] connections;

    public ChatServerChannel(DataInputStream stream, ChatConnection[] conns, String client) {
        dis = stream;
        connections = conns;
        clientName = client;
    }

    public ChatServerChannel(PrintStream stream, ChatConnection[] conns, String client) {
        ps = stream;
        connections = conns;
        clientName = client;
    }

    public void run() {
        if(dis == null) {
            // Writer mode

        }
        else if(ps == null) {
            // Reader mode
            BufferedReader br = new BufferedReader(new InputStreamReader(dis));
            String readMsg = null;
            try {
                while((readMsg = br.readLine()) != null) {
                    for(ChatConnection c : connections) {
                        if (c != null && !c.clientName.equals(clientName)) {
                            if(c.outStream != null) {
                                c.outStream.println(clientName+": "+readMsg);
                            }

                        }
                    }

                    System.out.println(clientName+": "+readMsg);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            // TODO Something ain't right!
        }
    }

}