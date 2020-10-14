package networkCompany;

import com.google.common.eventbus.EventBus;
import crypto.AlgorithmsTyp;

import java.util.ArrayList;

public class Channel implements  IChannel {
    private String name;
    private IParticipant participant01;
    private IParticipant participant02;
    private EventBus eventBusPart01;
    private EventBus eventBusPart02;
    private ArrayList<IParticipantIntruderListener> listenersArrayList;

    public Channel(String name, IParticipant participant01, IParticipant participant02) {
        this.name = name;
        this.participant01 = participant01;
        this.participant02 = participant02;
        this.eventBusPart01 = new EventBus(name+"participant01");
        this.eventBusPart02 = new EventBus(name+"participant02");
        Subscriber test =(Subscriber)participant01;
        Subscriber test2 =(Subscriber)participant02;

        this.eventBusPart01.register(test);
        this.eventBusPart02.register(test2);
        listenersArrayList = new ArrayList<>();
    }


    public String getName() {
        return name;
    }


    public IParticipant getParticipant01() {
        return participant01;
    }


    public IParticipant getParticipant02() {
        return participant02;
    }


    public boolean send(String messageContent, AlgorithmsTyp algorithmsTyp,RSAPublicKey rsaPublicKey, IParticipant targetParticipant, String keyFileName, IParticipant participantFrom)
    {
        sendMessagetoIntruder(new EventMessageSend(messageContent,algorithmsTyp,rsaPublicKey,keyFileName,participantFrom,targetParticipant));

        if (targetParticipant.getName().equals(participant01.getName())){
            eventBusPart01.post(new EventMessageSend(messageContent,algorithmsTyp,rsaPublicKey,keyFileName,participantFrom,targetParticipant));
            return true;
        }else if(targetParticipant.getName().equals(participant02.getName())){
            eventBusPart02.post(new EventMessageSend(messageContent,algorithmsTyp,rsaPublicKey,keyFileName,participantFrom,targetParticipant));
            return  true;
        }else{
            return  false;
        }

    }

    public boolean send(String messageContent, AlgorithmsTyp algorithmsTyp, IParticipant targetParticipant,String keyFileName, IParticipant participantFrom)
    {
        sendMessagetoIntruder(new EventMessageSend(messageContent,algorithmsTyp,keyFileName,participantFrom,targetParticipant));

        if (targetParticipant.getName().equals(participant01.getName())){
            eventBusPart01.post(new EventMessageSend(messageContent,algorithmsTyp,keyFileName,participantFrom,targetParticipant));
            return true;
        }else if(targetParticipant.getName().equals(participant02.getName())){
            eventBusPart02.post(new EventMessageSend(messageContent,algorithmsTyp,keyFileName,participantFrom,targetParticipant));
            return  true;
        }else{
            return  false;
        }

    }

    public void addListener(IParticipantIntruderListener listener){
        listenersArrayList.add(listener);
    }

    public void sendMessagetoIntruder(EventMessageSend message){
        for (IParticipantIntruderListener listener: listenersArrayList) {
            listener.message(message);

        }
    }
}

