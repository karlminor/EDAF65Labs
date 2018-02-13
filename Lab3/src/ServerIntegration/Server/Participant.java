package ServerIntegration.Server;

import ServerIntegration.Server.Mailbox;

import java.io.*;
import java.net.Socket;

public class Participant extends Thread {
    private Socket socket;
    private Mailbox mailbox;
    private BufferedReader input;
    private BufferedWriter output;
    private String name;
    private boolean status;

    public Participant(Socket socket, Mailbox mailbox, int number) {
        this.socket = socket;
        this.mailbox = mailbox;
        name = "User " + Integer.toString(number);
        status = true;
    }

    public void run() {
        System.out.println(name + " has joined with address: " +
                socket.getInetAddress());
        try {
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            while (socket.isConnected()) {
                handleConnection();
            }
            disconnect();
        } catch (IOException e) {
            System.out.println(name + " has left");
            status = false;
        } catch (InterruptedException e) {
            System.out.println(name + " has left");
            status = false;
        }
    }

    public boolean status() {
        return status;
    }

    public String name() {
        return name;
    }


    private void handleConnection() throws IOException, InterruptedException {
        String message = input.readLine();
        if (message != null) {
            String[] msg = message.split(":");
            System.out.println(message);
            switch (msg[0]) {
                case ("M"):
                    broadcast(name + ": " + msg[1]);
                    break;
                case ("E"):
                    System.out.println("Echo: " + msg[1] + " to " + name);
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
    }

    public void echo(String msg) throws IOException {
        System.out.println("Writing " + msg + " to " + name);
        output.write(msg + "\n");
        output.flush();
    }

    private void disconnect() throws IOException {
        status = false;
        input.close();
        output.close();
        socket.close();
    }

    private void broadcast(String msg) throws InterruptedException {
        System.out.println("Broadcast " + msg + " from " + name);
        mailbox.deposit(msg);
    }

}