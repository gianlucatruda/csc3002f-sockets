import java.io.*;
import java.util.Scanner;

public class ChatClientChannel extends Thread {

    DataInputStream dis = null;
    PrintStream ps = null;

    public ChatClientChannel(DataInputStream stream) {
        dis = stream;
    }

    public ChatClientChannel(PrintStream stream) {
        ps = stream;
    }

    public void run() {
        if(dis == null) {
            // Writer mode
            System.out.print("> ");
            Scanner myScan = new Scanner(System.in);
            while(myScan.hasNextLine()) {
                String msg = myScan.nextLine();
                ps.println(msg);
                ps.flush();
                System.out.print("> ");
            }

        }
        else if(ps == null) {
            // Reader mode
            BufferedReader br = new BufferedReader(new InputStreamReader(dis));
            String readMsg = null;
            try {
                while((readMsg = br.readLine()) != null) {
                    System.out.println(readMsg);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            // TODO Something ain't right!
        }
    }

}
