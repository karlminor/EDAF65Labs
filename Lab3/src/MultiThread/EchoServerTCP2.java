package MultiThread;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServerTCP2 {
    int port = 30000;

    public static void main(String[] args) {
        new EchoServerTCP2().run();
    }

    public void run(){
        try {
            ServerSocket ss = new ServerSocket(port);
            Socket socket;

            while(true) {
                socket = ss.accept();
                new Connection(socket).start();
            }

        } catch(IOException e){
            e.printStackTrace();
            System.exit(1);
        }
    }


}
