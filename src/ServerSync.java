import java.io.DataInputStream;
import java.io.PrintStream;
import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;

/*
 * A chat server that delivers public and private messages.
 */
public class ServerSync {
private static int port=3456;
private static ChatServer serv=new ChatServer();
private static ServerSocket SSocket=serv.listen(port);
private static Socket clientSocket = null;

  // This chat server can accept up to maxClientsCount clients' connections.
  private static final int max = 3;
  private static int count=0;
  private static final cThreads[] threads = new cThreads[max];

  public static void main(String args[]) {

    while (true) {
      try {
        clientSocket = SSocket.accept();
        if(count<max&&threads[count]==null){
          (threads[count]=new cThreads(clientSocket,threads)).start();

          count++;

        }
        else{
          PrintStream os = new PrintStream(clientSocket.getOutputStream());
          os.println("Server too busy. Try later.");
          os.close();
          clientSocket.close();
        }
      } catch (IOException e) {
        System.out.println(e);
      }
    }
  }
}


class cThreads extends Thread {

  private DataInputStream is = null;
  private PrintStream os = null;
  private Socket clientSocket = null;
  private final cThreads[] threads;
  private int max;

  public cThreads(Socket clientSocket, cThreads[] threads) {
    this.clientSocket = clientSocket;
    this.threads = threads;
    max = threads.length;
  }

  public void run() {
    int max = this.max;
    cThreads[] threads = this.threads;

  try {
      ins =new DataInputStream(clientSocket.getInputStream());
      outs =new PrintStream(clientSocket.getOutputStream());
      outs.println("Enter your name.");
      String name = is.readLine().trim();
      outs.println("Howdy," + name + " and welcome to our groovy chat room.\nTo leave enter /quit in a new line");
      for (int i = 0; i < max; i++) {//send to all others
        if (threads[i] != null && threads[i] != this) {
          threads[i].os.println("*** A new user " + name
              + " entered the chat room !!! ***");
        }
      }
      while (true) {
        String line = ins.readLine();
        if (line.startsWith("/quit")) {
          break;
        }
        for (int i = 0; i < max ;i++) {
          if (threads[i] != null) {
            threads[i].outs.println("<" + name + "&gr; " + line);
          }
        }
      }
      for (int i = 0; i < max; i++) {
        if (threads[i] != null && threads[i] != this) {
          threads[i].os.println("*** The user " + name
              + " is leaving the chat room !!! ***");
        }
      }
      os.println("*** Bye " + name + " ***");

      /*
       * Clean up. Set the current thread variable to null so that a new client
       * could be accepted by the server.
       */
      for (int i = 0; i < max; i++) {
        if (threads[i] == this) {
          threads[i] = null;
        }
      }

      /*
       * Close the output stream, close the input stream, close the socket.
       */
      is.close();
      os.close();
      clientSocket.close();
    } catch (IOException e) {
    }
  }
}
