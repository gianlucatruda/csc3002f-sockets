import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ChatServerChannel extends Thread {

    ArrayList<String> msgs;
    DataInputStream dis = null;
    PrintStream ps = null;

    public ChatServerChannel(DataInputStream stream, ArrayList<String> messages) {
        dis = stream;
        msgs = messages;
    }

    public ChatServerChannel(PrintStream stream, ArrayList<String> messages) {
        ps = stream;
        msgs = messages;
    }

    public void run() {
        if(dis == null) {
            // Writer mode
            Scanner myScan = new Scanner(System.in);
            while(myScan.hasNextLine()) {
                String msg = myScan.nextLine();
                ps.println(msg);
                ps.flush();
            }

        }
        else if(ps == null) {
            // Reader mode
            BufferedReader br = new BufferedReader(new InputStreamReader(dis));
            String readMsg = null;
            try {
                while((readMsg = br.readLine()) != null) {
                    System.out.println("Client: "+readMsg);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            // TODO Something ain't right!
        }
    }

}