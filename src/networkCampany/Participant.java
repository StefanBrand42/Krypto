package networkCampany;

import com.google.common.eventbus.Subscribe;
import crypto.AlgorithmsTyp;

public class Participant extends Subscriber implements IParticipant {
    private int id;
    private String name;
    private ParticipantTyp participantTyp;

    public Participant(int id, String name, ParticipantTyp participantTyp) {
        this.id = id;
        this.name = name;
        this.participantTyp = participantTyp;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ParticipantTyp getParticipantTyp() {
        return participantTyp;
    }


    // Event Bud
    @Subscribe
    public void receive(EventMessageSend eventMessageSend){
        System.out.println("---------------Message receive: Participant "+name);
        System.out.println("Message Content:"+eventMessageSend.getMessageContent() );
        System.out.println("Algo Type:"+ eventMessageSend.getAlgoTpy().toString() );
        if (eventMessageSend.getAlgoTpy()== AlgorithmsTyp.RSA){

            System.out.println("RSAPublicKey  n:"+eventMessageSend.getRsaPublicKey().getN().toString() );
            System.out.println("RSAPublicKey  e:"+eventMessageSend.getRsaPublicKey().getN().toString() );
        }

    }
}
