package networkCompany;

public class ParticipantIntruder implements IParticipantIntruderListener {
    public void message(EventMessageSend message){
        System.out.println(message.getMessageContent());
    }
}
