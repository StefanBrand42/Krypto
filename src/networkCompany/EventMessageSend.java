package networkCompany;

import crypto.AlgorithmsTyp;

public class EventMessageSend {
    private String messageContent;
    private AlgorithmsTyp algoTpy;
    private RSAPublicKey rsaPublicKey = null;
    private String keyFilename;
    private IParticipant participantFrom;


    public EventMessageSend(String messageContent, AlgorithmsTyp algoTpy, RSAPublicKey rsaPublicKey, String keyFileName, IParticipant participantFrom) {
        this.messageContent = messageContent;
        this.algoTpy = algoTpy;
        this.rsaPublicKey = rsaPublicKey;
        this.keyFilename = keyFileName;
        this.participantFrom = participantFrom;

    }

    public EventMessageSend(String messageContent, AlgorithmsTyp algoTpy, String keyFileName,IParticipant participantFrom) {
        this.messageContent = messageContent;
        this.algoTpy = algoTpy;
        this.keyFilename= keyFileName;
        this.participantFrom = participantFrom;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public AlgorithmsTyp getAlgoTpy() {
        return algoTpy;
    }

    public RSAPublicKey getRsaPublicKey() {
        return rsaPublicKey;
    }

    public String getKeyFilename() {
        return keyFilename;
    }

    public IParticipant getParticipantFrom() {
        return participantFrom;
    }
}
