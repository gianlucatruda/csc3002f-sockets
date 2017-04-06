import java.util.ArrayList;
import java.util.stream.Collectors;

import static java.lang.Thread.sleep;

public class ChatServer {

    public static void main(String[] args) throws InterruptedException {
        ArrayList<String> messages = new ArrayList<String>();
        ChatConnection[] conns = new ChatConnection[8];
        ArrayList<String> images = new ArrayList<>();

        ChatConnection s1 = new ChatConnection("Alice", 4001, conns, images);
        ChatConnection s2 = new ChatConnection("Bob", 4002, conns, images);

        conns[0] = s1;
        conns[1] = s2;

        s1.start();
        s2.start();


    }
}
