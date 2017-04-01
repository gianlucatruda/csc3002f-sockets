import java.util.ArrayList;
import java.util.stream.Collectors;

import static java.lang.Thread.sleep;

public class ChatServerHandler {

    public static void main(String[] args) throws InterruptedException {
        ArrayList<String> messages = new ArrayList<String>();
        messages.add("Welcome to the chat!");
        ChatConnection s1 = new ChatConnection("Alice", 4001, messages);
        ChatConnection s2 = new ChatConnection("Bob", 4002, messages);
        s1.start();
        s2.start();

        while(true) {
            sleep(100);
            messages.addAll(s1.getUndeliveredMessages().stream().collect(Collectors.toList()));
            messages.addAll(s2.getUndeliveredMessages().stream().collect(Collectors.toList()));
            //System.out.println("\r"+messages.toString());
        }

    }
}
