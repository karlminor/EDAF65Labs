package ServerIntegration;

import java.io.IOException;

public class Postman extends Thread{
    Participants participants;
    Mailbox mailbox;

    public Postman(Participants participants, Mailbox mailbox){
        this.participants = participants;
        this.mailbox = mailbox;
    }

    public void run(){
        String message;
        while(true){
            try {
                message = mailbox.withdraw();
                for (Participant p : participants.retrieveParticipants()){
                    p.echo(message);
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}