package ServerIntegration.Client;

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
                    String[] msg = message.split(":");
                    if(msg[0].equals("Q")){
                        System.out.println("Exiting");
                        System.exit(0);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}