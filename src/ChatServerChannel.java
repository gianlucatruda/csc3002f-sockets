import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class ChatServerChannel extends Thread {

    ArrayList<String> msgs;
    DataInputStream dis = null;
    PrintStream ps = null;
    ChatConnection[] connections;
    public String clientName = "ANON.";

    public ChatServerChannel(DataInputStream stream, ChatConnection[] conns) {
        dis = stream;
        connections = conns;
    }

    public ChatServerChannel(PrintStream stream, ChatConnection[] conns) {
        ps = stream;
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
                    // If the <name> tag is used
                    if(readMsg.startsWith("<name>")) {
                        String oldName = clientName;
                        clientName = readMsg.substring(6,readMsg.length());
                        for(ChatConnection c : connections) {
                            if (c != null) {
                                if(c.outStream != null) {
                                    if(!c.getClientName().equals(clientName)) {
                                        c.outStream.println("> '"+oldName+"' changed name to '"+clientName+"'");
                                    }
                                }

                            }
                        }
                    }
                    // If the <img> tag is used
                    else if (readMsg.startsWith("<img>")) {
                        int byteCount = Integer.parseInt(readMsg.substring(5, readMsg.length()));
                        System.out.println("> Receiving image from '"+clientName+"' ("+byteCount+" b)");

                        try {
                            byte[] bArray = new byte[byteCount];
                            for (int i = 0; i < byteCount; i++) {
                                bArray[i] = dis.readByte();
                            }
                            FileOutputStream fos = new FileOutputStream("received.png");
                            fos.write(bArray);
                            fos.close();
                        }
                        catch (Exception e) {
                            System.out.println("Oh no...");
                        }

                    }
                    // Regular message
                    else {
                        for(ChatConnection c : connections) {
                            if (c != null) {
                                if(c.outStream != null) {
                                    if(!c.getClientName().equals(clientName)) {
                                        c.outStream.println(clientName+": "+readMsg);
                                    }
                                }

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