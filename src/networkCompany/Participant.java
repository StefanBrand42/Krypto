package networkCompany;

import com.google.common.eventbus.Subscribe;
import crypto.AlgorithmsTyp;
import crypto.CryptoCreator;
import persistence.HSQLTablePostboxs;

public class Participant extends Subscriber implements IParticipant {
    private int id;
    private String name;
    private ParticipantTyp participantTyp;
    private IParticipantIntruderListener participantIntruder ;

    public Participant(int id, String name, ParticipantTyp participantTyp) {
        this.id = id;
        this.name = name;
        this.participantTyp = participantTyp;
        if (participantTyp == ParticipantTyp.intruder){
            participantIntruder = new ParticipantIntruder(this);
        }else{
            participantIntruder = null;
        }
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ParticipantTyp getParticipantTyp() {
        return participantTyp;
    }

    public IParticipantIntruderListener getParticipantIntruder() {
        return participantIntruder;
    }

    // Event Bud
    @Subscribe
    public void receive(EventMessageSend eventMessageSend){
        CryptoCreator cryptoCreator = new CryptoCreator();
        String decrypt = cryptoCreator.decryptMessage(eventMessageSend.getMessageContent(),eventMessageSend.getAlgoTpy().toString(),eventMessageSend.getKeyFilename());
        System.out.println("---------------Message receive: Participant "+name);
        System.out.println("Message Content:"+eventMessageSend.getMessageContent() );
        System.out.println("Algo Type:"+ eventMessageSend.getAlgoTpy().toString() );
        System.out.println("Message encryptetd: "+eventMessageSend.getMessageContent());
        System.out.println("Message decrypted: "+decrypt);
        if (eventMessageSend.getAlgoTpy()== AlgorithmsTyp.RSA){

            System.out.println("RSAPublicKey  n:"+eventMessageSend.getRsaPublicKey().getN().toString() );
            System.out.println("RSAPublicKey  e:"+eventMessageSend.getRsaPublicKey().getE().toString() );
        }

        CompanyNetControlCenter.instance.getGui().writeTextAreaGui(name +" received new message");
        HSQLTablePostboxs.instance.insertDataTablePostbox(name,eventMessageSend.getParticipantFrom().getId(),decrypt);



    }
}
