package networkCompany;

import crypto.AlgorithmsTyp;

public interface IChannel {
    String getName();
    IParticipant getParticipant01();
    IParticipant getParticipant02();
    boolean send(String messageContent, AlgorithmsTyp algorithmsTyp,RSAPublicKey rsaPublicKey, IParticipant targetParticipant, String keyFileName, IParticipant participantFrom);
    boolean send(String messageContent, AlgorithmsTyp algorithmsTyp, IParticipant targetParticipant,String keyFileName, IParticipant participantFrom);


}