import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ChatServerChannel extends Thread {

    ArrayList<String> msgs;
    DataInputStream dis = null;
    PrintStream ps = null;
    String clientName = "CLIENT";
    ChatConnection[] connections;

    public ChatServerChannel(DataInputStream stream, ArrayList<String> messages, ChatConnection[] conns) {
        dis = stream;
        msgs = messages;
        connections = conns;
    }

    public ChatServerChannel(PrintStream stream, ArrayList<String> messages, ChatConnection[] conns) {
        ps = stream;
        msgs = messages;
        connections = conns;
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
                        if (c != null) {
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