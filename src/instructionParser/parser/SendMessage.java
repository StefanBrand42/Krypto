package instructionParser.parser;

import configuration.Configuration;
import crypto.AlgorithmsTyp;
import gui.GUI;
import instructionParser.ParserInstruction;
import networkCampany.CompanyNetControlCenter;
import networkCampany.IChannel;
import networkCampany.IParticipant;
import persistence.HSQLTableMessages;

public class SendMessage extends  ParserInstruction {



    public SendMessage(ParserInstruction sucessor) {
        this.setSuccessor(sucessor);

    }

    public void parse(String commandLine, GUI gui) {
        String[] commandLineArray = commandLine.split(" ");
        if (commandLine.matches("send message \"(.+)\" from (.+) to (.+) using (.+) and keyfile (.+)") && commandLineArray.length == 12){
            gui.writeTextAreaGui("Instruction send message");
            String message_ = commandLineArray[2];
            String message = message_.replace("\"","");
            String participant01 = commandLineArray[4];
            String participant02 = commandLineArray[6];
            String algo = commandLineArray[8];
            String keyfileName = commandLineArray[11];


            boolean readyforSend = true  ;
            StringBuilder stringBuilder01 = new StringBuilder();
            if (!Configuration.instance.checkIfAlgoExist(algo)){
                stringBuilder01.append("Your chosen algorithm dos not exit \n");
                readyforSend = false;
            }
            if (!Configuration.instance.checkIfKeyFileNameExist(keyfileName)){
                stringBuilder01.append("Your chosen keyfilename dos not exit (please without .json) \n");
                readyforSend = false;
            }
            if (!CompanyNetControlCenter.instance.isChannelExist(participant01,participant02)){
                stringBuilder01.append("no valid channel from "+participant01 +" to "+participant02+" \n");
                readyforSend = false;
            }

            if (readyforSend)
            {
                IChannel channel = CompanyNetControlCenter.instance.getChannelByNamePartic01Part02(participant01,participant02);
                String encrpytMassage = gui.getCryptoCreator().encryptMessage(message,algo,keyfileName);
                IParticipant participantTarget = CompanyNetControlCenter.instance.getParticipantByName(participant02);
                IParticipant participantFrom = CompanyNetControlCenter.instance.getParticipantByName(participant01);

                // Send Message via EventBus
                AlgorithmsTyp algorithmsTyp = gui.getCryptoCreator().getAlgoTypFromName(algo);
                switch (algorithmsTyp){
                    case RSA:
                       break;
                    case SHIFT:
                        channel.send(encrpytMassage,algorithmsTyp,participantTarget,keyfileName);
                        HSQLTableMessages.instance.insertDataTableMessages(participantFrom.getId(),participantTarget.getId(),message,algo.toLowerCase(),encrpytMassage,keyfileName);

                        break;
                }


            }else {
                gui.writeTextAreaGui(stringBuilder01.toString());
            }


        }else{

            super.parse(commandLine,gui);
        }


    }
}
