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
    ArrayList<String> images;
    public String clientName = "ANON.";

    public ChatServerChannel(DataInputStream stream, ChatConnection[] conns, ArrayList<String> imgs) {
        dis = stream;
        connections = conns;
        images = imgs;
    }

    public ChatServerChannel(PrintStream stream, ChatConnection[] conns, ArrayList<String> imgs) {
        ps = stream;
        connections = conns;
        images = imgs;
    }

    public void run() {
        if(dis == null) {
            // Writer mode

        }
        else if(ps == null) {
            // Reader mode

            for(ChatConnection c : connections) {
                if (c != null) {
                    if (c.outStream != null) {
                        if (c.getClientName().equals(clientName)) {
                            ps = c.outStream;
                        }
                    }
                }
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(dis));
            String readMsg = null;
            try {
                while((readMsg = br.readLine()) != null) {
                    // If the <name> tag is used
                    if(readMsg.startsWith("<name>")) {
                        String oldName = clientName;
                        clientName = readMsg.substring(6,readMsg.length());
                        System.out.println("> '"+oldName+"' changed name to '"+clientName+"'");
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
                        String imageName = "received"+String.valueOf(Math.random()).substring(2,5)+".png";
                        try {
                            byte[] bArray = new byte[byteCount];
                            for (int i = 0; i < byteCount; i++) {
                                bArray[i] = dis.readByte();
                            }

                            FileOutputStream fos = new FileOutputStream(imageName);
                            fos.write(bArray);
                            fos.close();
                            images.add(imageName);
                            System.out.println("> Image saved: "+imageName);

                            for(ChatConnection c : connections) {
                                if (c != null) {
                                    if(c.outStream != null) {
                                        if(!c.getClientName().equals(clientName)) {
                                            try {
                                                c.outStream.println("> '"+clientName+"' sent you an image.");
                                                c.outStream.println("<img-req>"+bArray.length);
                                                c.outStream.flush();
                                            }
                                            catch (Exception e) {
                                                System.out.println("> ERROR: Could not forward image request!");
                                            }

                                        }
                                    }

                                }
                            }

                        }
                        catch (Exception e) {
                            System.out.println("> ERROR: Could not receive image!");
                        }

                    }
                    else if (readMsg.startsWith("<get>")) {
                        String pathname = images.get(images.size()-1);
                        System.out.println("> Sending '"+pathname+"' to '"+clientName+"'");
                        File file = new File(pathname);
                        byte[] bArray = new byte[(int) file.length()];

                        try {
                            ps.println("<img>" + bArray.length);
                            ps.flush();

                            FileInputStream fis = new FileInputStream(file);
                            fis.read(bArray);
                            fis.close();
                            ps.write(bArray, 0, bArray.length);

                            System.out.println("> '" + pathname + "' sent.");
                        }
                        catch (Exception e) {
                            System.out.println("> ERROR: Could not on-send image!\n" + e);
                        }
                    }
                    // Regular message
                    else {
                        System.out.println(clientName+": "+readMsg);
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
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            // TODO Something ain't right!
        }
    }

}