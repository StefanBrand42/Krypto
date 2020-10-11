package networkCampany;

import crypto.AlgorithmsTyp;

public class EventMessageSend {
    private String messageContent;
    private AlgorithmsTyp algoTpy;
    private RSAPublicKey rsaPublicKey = null;
    private String keyFilename;


    public EventMessageSend(String messageContent, AlgorithmsTyp algoTpy, RSAPublicKey rsaPublicKey, String keyFileName) {
        this.messageContent = messageContent;
        this.algoTpy = algoTpy;
        this.rsaPublicKey = rsaPublicKey;
        this.keyFilename = keyFileName;

    }

    public EventMessageSend(String messageContent, AlgorithmsTyp algoTpy, String keyFileName) {
        this.messageContent = messageContent;
        this.algoTpy = algoTpy;
        this.keyFilename= keyFileName;
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
}
