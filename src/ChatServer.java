import java.util.ArrayList;

public class ChatServer {

    public static void main(String[] args) throws InterruptedException {
        ChatConnection[] conns = new ChatConnection[8];
        ArrayList<String> images = new ArrayList<>();

        ChatConnection s1 = new ChatConnection("Server1", 4001, conns, images);
        ChatConnection s2 = new ChatConnection("Server2", 4002, conns, images);

        conns[0] = s1;
        conns[1] = s2;

        s1.start();
        s2.start();


    }
}
