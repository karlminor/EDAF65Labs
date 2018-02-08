package MultiThread;

import java.io.*;
import java.net.Socket;

public class Connection extends Thread{
    Socket socket;

    public Connection(Socket socket){
       this.socket = socket;
    }

    public void run(){
        System.out.println("Klient adress: " +
                socket.getInetAddress());
        try {
            handleConnection(socket);
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void handleConnection(Socket s) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));
        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));

        String message = input.readLine();
        while(!message.equals("exit")){
            System.out.println(message);
            reply(output,"du skickade: " +message);
            message = input.readLine();
        }
        reply(output, "hej då");
        input.close();
        output.close();
    }

    private void reply(BufferedWriter output, String message) throws IOException {
        output.write(message + "\n");
        output.flush();
    }
}