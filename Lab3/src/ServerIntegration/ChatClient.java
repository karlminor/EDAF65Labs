package ServerIntegration;

import java.io.*;
import java.net.Socket;
import java.nio.Buffer;
import java.util.Scanner;

public class ChatClient {
    static String host;
    static int port;
    BufferedReader input;
    BufferedWriter output;

    public static void main(String[] args){
        host = args[0];
        port = Integer.parseInt(args[1]);
        new ChatClient().run();
    }

    public void run(){
        try {
            Socket socket = new Socket(host, port);
            setupConnection(socket);
            new ClientSender(output).start();
            new ClientReceiver(input, socket).start();
            while(true){

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setupConnection(Socket socket) throws IOException {
        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }
}