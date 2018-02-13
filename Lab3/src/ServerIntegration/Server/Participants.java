package ServerIntegration.Server;

import ServerIntegration.Server.Participant;

import java.util.Collection;
import java.util.Vector;

public class Participants{
    Vector<Participant> participants;

    public Participants(){
        participants = new Vector<>();
    }

    public synchronized void add(Participant p){
        participants.add(p);
    }

    public synchronized Collection<Participant> retrieveParticipants(){
        return (Collection<Participant>)participants.clone();
    }

    public synchronized void remove(Participant p){
        participants.remove(p);
    }

    public synchronized int size(){ return participants.size();}
}