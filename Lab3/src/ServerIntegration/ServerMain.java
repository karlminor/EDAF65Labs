package ServerIntegration;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain {
    int port = 30000;
    int number = 0;

    public static void main(String[] args) {
        new ServerMain().run();
    }

    public void run(){
        try {
            ServerSocket ss = new ServerSocket(port);
            Socket socket;
            Participants participants = new Participants();
            Mailbox mailbox = new Mailbox();
            Participant p;
            Postman postman = new Postman(participants, mailbox);
            postman.start();

            while(true) {
                socket = ss.accept();
                p = new Participant(socket, mailbox, number);
                number++;
                participants.add(p);
                p.start();
            }

        } catch(IOException e){
            e.printStackTrace();
            System.exit(1);
        }
    }
}