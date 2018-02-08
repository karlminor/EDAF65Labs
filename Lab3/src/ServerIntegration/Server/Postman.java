package ServerIntegration.Server;

import ServerIntegration.Server.Mailbox;
import ServerIntegration.Server.Participant;
import ServerIntegration.Server.Participants;

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
                    if (p.status()) {
                        p.echo(message);
                    }
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