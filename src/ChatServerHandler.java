public class ChatServerHandler {

    public static void main(String[] args) {
        ChatServerThread s1 = new ChatServerThread("Alice", 4001);
        ChatServerThread s2 = new ChatServerThread("Bob", 4002);
        s1.start();
        s2.start();

    }
}
