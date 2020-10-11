package networkCampany;

public class EventMessageSend {
    private  String messageContent;
    private  String algo;
    private  String PublicKey; // für RSA n und e


    public EventMessageSend(String messageContent) {
        this.messageContent = messageContent;
    }

    public String getMessageContent() {
        return messageContent;
    }
}
