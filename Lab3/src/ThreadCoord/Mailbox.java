package ThreadCoord;


public class Mailbox{
    String message = "";

    public Mailbox(){
    }

    public synchronized void deposit(String message) throws InterruptedException {
        while(!this.message.isEmpty()){
            wait();
        }
        this.message = message;

    }

    public synchronized String withdraw(){
        String temp = message;
        message = "";
        notifyAll();
        return temp;
    }
}