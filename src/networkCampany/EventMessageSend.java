package networkCampany;

import crypto.AlgorithmsTyp;

public class EventMessageSend {
    private String messageContent;
    private AlgorithmsTyp algoTpy;
    private RSAPublicKey rsaPublicKey = null;


    public EventMessageSend(String messageContent, AlgorithmsTyp algoTpy, RSAPublicKey rsaPublicKey) {
        this.messageContent = messageContent;
        this.algoTpy = algoTpy;
        this.rsaPublicKey = rsaPublicKey;
    }

    public EventMessageSend(String messageContent, AlgorithmsTyp algoTpy) {
        this.messageContent = messageContent;
        this.algoTpy = algoTpy;
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
}
