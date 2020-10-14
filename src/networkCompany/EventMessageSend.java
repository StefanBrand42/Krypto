package networkCompany;

import crypto.AlgorithmsTyp;

public class EventMessageSend {
    private String messageContent;
    private AlgorithmsTyp algoTpy;
    private RSAPublicKey rsaPublicKey = null;
    private String keyFilename;
    private IParticipant participantFrom;
    private IParticipant participantTarget;


    public EventMessageSend(String messageContent, AlgorithmsTyp algoTpy, RSAPublicKey rsaPublicKey, String keyFileName, IParticipant participantFrom, IParticipant participantTarget) {
        this.messageContent = messageContent;
        this.algoTpy = algoTpy;
        this.rsaPublicKey = rsaPublicKey;
        this.keyFilename = keyFileName;
        this.participantFrom = participantFrom;
        this.participantTarget = participantTarget;

    }

    public EventMessageSend(String messageContent, AlgorithmsTyp algoTpy, String keyFileName,IParticipant participantFrom,IParticipant participantTarget) {
        this.messageContent = messageContent;
        this.algoTpy = algoTpy;
        this.keyFilename= keyFileName;
        this.participantFrom = participantFrom;
        this.participantTarget = participantTarget;
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

    public IParticipant getParticipantTarget() {
        return participantTarget;
    }
}
