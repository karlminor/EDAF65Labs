package ThreadCoord;

public class WithdrawThread extends Thread{
    Mailbox mailbox;

    public WithdrawThread(Mailbox mailbox){
        this.mailbox = mailbox;
    }

    public void run(){
        while(true){
            try {
                System.out.println(mailbox.withdraw());
                this.sleep((long)(Math.random()*1000+500));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}