package ThreadCoord;

public class DepositThread extends Thread{
    Mailbox mailbox;

    public DepositThread(Mailbox mailbox){
        this.mailbox = mailbox;
    }

    public void run(){
        for (int i = 0; i < 5; i++){
            try {
                mailbox.deposit(this.getName());
                this.sleep((long)(Math.random()*1000+500));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}