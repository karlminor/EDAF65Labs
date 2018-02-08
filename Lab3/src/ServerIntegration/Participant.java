package ServerIntegration;

import java.io.*;
import java.net.Socket;

public class Participant extends Thread{
    Socket socket;
    Mailbox mailbox;
    BufferedReader input;
    BufferedWriter output;
    String name;

    public Participant(Socket socket, Mailbox mailbox, int number){
        this.socket = socket;
        this.mailbox = mailbox;
        name = "User " + Integer.toString(number);
    }

    public void run(){
        System.out.println("Klient adress: " +
                socket.getInetAddress());
        try {
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            while(socket.isConnected()){
                handleConnection();
            }
            disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private void handleConnection() throws IOException, InterruptedException {
            String message = input.readLine();
            String[] msg = message.split(":");
            switch (msg[0]) {
                case ("M"):
                    broadcast(name + ": " + msg[1]);
                    break;
                case ("E"):
                    echo(msg[1]);
                    break;
                case ("Q"):
                    broadcast(name + " has left");
                    disconnect();
                    break;
                default:
                    echo("Incorrect entry");
                    break;
            }
    }

    public void echo(String msg) throws IOException {
        output.write(msg + "\n");
        output.flush();
    }

    private void disconnect() throws IOException {
        input.close();
        output.close();
        socket.close();
    }

    private void broadcast(String msg) throws InterruptedException {
        mailbox.deposit(msg);
    }

}