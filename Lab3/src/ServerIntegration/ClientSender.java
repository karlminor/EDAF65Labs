package ServerIntegration;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Scanner;

public class ClientSender extends Thread{
    BufferedWriter output;
    Scanner keyboard;

    public ClientSender(BufferedWriter output){
        this.output = output;
        keyboard = new Scanner(System.in);
    }

    public void run(){
        String message;
        while(true){
            if(keyboard.hasNext()) {
                message = keyboard.nextLine();
                try {
                    output.write(message + "\n");
                    output.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}