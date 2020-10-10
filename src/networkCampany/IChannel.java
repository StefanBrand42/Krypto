package networkCampany;

public interface IChannel {
    String getName();
    IParticipant getParticipant01();
    IParticipant getParticipant02();
    boolean send(String messageContent,IParticipant targetParticipant);


}