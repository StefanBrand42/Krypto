package networkCampany;

import com.google.common.eventbus.EventBus;

public class Channel implements  IChannel {
    private String name;
    private IParticipant participant01;
    private IParticipant participant02;
    private EventBus eventBusPart01;
    private EventBus eventBusPart02;

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


    public boolean send(String messageContent,IParticipant targetParticipant)
    {

        if (targetParticipant.getName().equals(participant01.getName())){
            eventBusPart01.post(new EventMessageSend(messageContent));
            return true;
        }else if(targetParticipant.getName().equals(participant02.getName())){
            eventBusPart02.post(new EventMessageSend(messageContent));
            return  true;
        }else{
            return  false;
        }



    }
}
