import java.io.*;
import java.util.Scanner;
import java.io.File;
import java.io.FileInputStream;
//import java.System;

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
                //to send file <file>file.jpg
                if(msg.startsWith("<file>")){
                  ps.write(msg);
                  ps.flush();
                  String filex=msg.substring(6,msg.length());
                  File file=new File(filex);
                  byte[] mybytearray = new byte[(int) file.length()];//create byte array of size
                  BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));//create buffer stream of file
                  //bis.read(mybytearray, 0, mybytearray.length);

                  ps.write(mybytearray, 0, mybytearray.length);
                  ps.flush();
                }
                else{


                ps.println(msg);
                ps.flush();
                System.out.print("> ");
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
