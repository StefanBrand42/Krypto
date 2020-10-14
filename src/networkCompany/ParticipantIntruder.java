package networkCompany;

import configuration.Configuration;
import crypto.AlgorithmsTyp;
import crypto.Cracker;
import persistence.HSQLTablePostboxs;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ParticipantIntruder implements IParticipantIntruderListener {
    private Participant participant;


    public ParticipantIntruder(Participant participant) {
        this.participant = participant;
    }

    public void message(EventMessageSend message){
        System.out.println(message.getMessageContent());

        AlgorithmsTyp algorithmsTyp = message.getAlgoTpy();
        int id = HSQLTablePostboxs.instance.insertDataTablePostboxAndGetId(participant.getName(),message.getParticipantFrom().getId(),"unknown");

        Future<String> future = new Cracker().cracking(message.getAlgoTpy(), message.getMessageContent(), message.getRsaPublicKey());

        try {
            String decryptMessage = future.get(Configuration.instance.TimeoutTimeSeconds, TimeUnit.SECONDS);

            CompanyNetControlCenter.instance.getGui().writeTextAreaGui("intruder "+participant.getName()+" cracked message from participant "+message.getParticipantFrom().getName()+" | "+decryptMessage);
            if (decryptMessage.length()>49){
                decryptMessage = decryptMessage.substring(0,49);
            }

            String decryptMessage2 = decryptMessage.replace("\n"," ");
            HSQLTablePostboxs.instance.changeMessage(participant.getName(),id,decryptMessage2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            CompanyNetControlCenter.instance.getGui().writeTextAreaGui("intruder " +participant.getName()+" | crack message from participant "+ message.getParticipantFrom().getName()+" failed");
            future.cancel(true);
        }


    }
}
