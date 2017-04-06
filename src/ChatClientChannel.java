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
                    String pathname = msg.substring(5, msg.length());
                    if(!pathname.substring(pathname.length()-3,pathname.length()).equals("png")) {
                        System.out.println("> NOTE: Currently, only .png files are supported.");
                    } else {
                        File file = new File(pathname);
                        if(!file.exists()) {
                            System.out.println("> ERROR: Could not find that image.");
                        } else {
                            byte[] bArray = new byte[(int) file.length()];
                            try {
                                ps.println("<img>"+bArray.length);
                                ps.flush();

                                FileInputStream fis = new FileInputStream(file);
                                fis.read(bArray);
                                fis.close();
                                ps.write(bArray, 0, bArray.length);

                                System.out.println("> '"+pathname+"' sent.");

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }

                }
                else if (msg.startsWith("<get>")) {
                    ps.println(msg);
                    ps.flush();
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
                    //System.out.println("DEBUG: "+readMsg);
                    if (readMsg.startsWith("<img-req>")) {
                        int byteCount = Integer.parseInt(readMsg.substring(9, readMsg.length()));
                        System.out.println("> Type <get> to download ("+(byteCount/1000)+"kB).");
                    }
                    else if (readMsg.startsWith("<img>")) {
                        int byteCount = Integer.parseInt(readMsg.substring(5, readMsg.length()));
                        System.out.println("> Receiving image (" + byteCount + " b)");
                        String imageName = "received" + String.valueOf(Math.random()).substring(2, 6) + ".png";
                        try {
                            byte[] bArray = new byte[byteCount];
                            for (int i = 0; i < byteCount; i++) {
                                bArray[i] = dis.readByte();
                            }
                            FileOutputStream fos = new FileOutputStream(imageName);
                            fos.write(bArray);
                            fos.close();
                            System.out.println("> Image saved: " + imageName);
                        } catch (Exception e) {
                            System.out.println("> ERROR: Could not receive message.");
                        }
                    }
                    else {
                        System.out.println(readMsg);
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
