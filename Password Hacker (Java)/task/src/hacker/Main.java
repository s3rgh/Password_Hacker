package hacker;

public class Main {
    public static void main(String[] args) {
        ClientSocket clientSocket = new ClientSocket();
        clientSocket.sendMessage(args);
    }
}
