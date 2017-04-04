import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class ChatClientChannel extends Thread {

    DataInputStream dis = null;
    PrintStream ps = null;
    String userName;

    public ChatClientChannel(DataInputStream stream, String name) {
        dis = stream;
        userName = name;
    }

    public ChatClientChannel(PrintStream stream, String name) {
        ps = stream;
        userName = name;
    }

    public void run() {
        if(dis == null) {
            // Writer mode

            // Tell server the username
            ps.println("<name>"+userName);
            ps.flush();

            Scanner myScan = new Scanner(System.in);
            while(myScan.hasNextLine()) {
                String msg = myScan.nextLine();

                if (msg.startsWith("<img>")) {
                    File file = new File(msg.substring(5, msg.length()));
                    byte[] bArray = new byte[(int) file.length()];
                    try {
                        FileInputStream fis = new FileInputStream(file);
                        fis.read(bArray);


                        ps.println("<img>"+bArray.length);
                        ps.flush();
                        ps.write(bArray, 0, bArray.length);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    ps.println(msg);
                    ps.flush();
                }
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
