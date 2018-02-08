package SingleThread;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoTCP1 {
    int port = 30000;

    public static void main(String[] args) {
	    new EchoTCP1().run();
    }

    public void run(){
        try {
            ServerSocket ss = new ServerSocket(port);
            Socket socket;

            while(true) {
                socket = ss.accept();
                System.out.println("Klient adress: " +
                        socket.getInetAddress());
                handleConnection(socket);
                socket.close();
            }

        } catch(IOException e){
            e.printStackTrace();
            System.exit(1);
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
