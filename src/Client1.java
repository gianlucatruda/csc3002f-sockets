import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.io.BufferedInputStream;

import static java.lang.Thread.sleep;
//import ChatClient;
public class Client1 implements Runnable{
  static DataInputStream ins = null;
  static  PrintStream outs = null;
  static DataInputStream sysins=null;
  static boolean check=false;
public static void main(String[] args) throws IOException, InterruptedException {
  DataInputStream sys123=new DataInputStream(new BufferedInputStream(System.in));
  System.out.println("enter your ip port no");

  String x=sys123.readLine().trim();

    ChatClient cc=new ChatClient();
    Socket myConnection=cc.openSocket("localhost",Integer.parseInt(x));
    System.out.println("host: Client1");
    //addd something with args to get host and port number
    System.out.println("using localhost port number: 3456");
    ins = cc.openInputStream(myConnection);
    outs = cc.openOutputStream(myConnection);
    sysins=new DataInputStream(new BufferedInputStream(System.in));
    if(myConnection != null) { //TODO tidy
        System.out.println("Connection established");


        if(ins != null && outs != null &&sysins!=null) {  //All set up-now to start connection
          try{ //thread toread from server
            new Thread(new Client1()).start();
            while(!check){
              outs.println(sysins.readLine().trim());
            }
            cc.closeConnection(myConnection,ins,outs);//close inout
          }
          catch(IOException err){
            System.err.println(err);
          }
        }
}
    }
    public void run(){
      String res;
      try{
        while((res=ins.readLine())!=null){
          System.out.println(res);

          if(res.equals("exit")){
            break;
          }
          check=true;
        }
      //  System.out.println("to send a file: ")
      }catch(IOException err){
        System.err.println(err);
      }
    }
}
/*  String reply=null;
  while(!sysins.equals("exit")){
    System.out.println("Type any text. To quit it type 'exit'.");
    outs.println(sysins.readLine()); //send what typed to server
    while ((reply= ins.readLine()) != null)
     {
      System.out.println("server says: "+reply);

          break;

    //  os.println(inputLine.readLine());
    }
}
cc.closeConnection(myConnection,ins,outs);
*/
