package ThreadCoord;
public class ThreadMachine {

    public static void main(String[] args) {
        new ThreadMachine().run();
    }

    public void run(){
        Mailbox mailbox = new Mailbox();
        for(int i = 0; i < 10; i++){
            new DepositThread(mailbox).start();
        }
        new WithdrawThread(mailbox).start();
    }
}