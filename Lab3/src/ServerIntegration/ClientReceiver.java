package ServerIntegration;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;

public class ClientReceiver extends Thread{
    BufferedReader input;
    Socket s;

    public ClientReceiver(BufferedReader input, Socket s){
        this.input = input;
        this.s = s;
    }

    public void run(){
        String message;
        while(true){
            if(!s.isClosed()){
                try {
                    message = input.readLine();
                    if(message != null) {
                        System.out.println(input.readLine());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            System.exit(0);
        }
    }
}