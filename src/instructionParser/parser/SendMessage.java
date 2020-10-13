package instructionParser.parser;

import configuration.Configuration;
import crypto.AlgorithmsTyp;
import gui.GUI;
import instructionParser.ParserInstruction;
import networkCompany.CompanyNetControlCenter;
import networkCompany.IChannel;
import networkCompany.IParticipant;
import networkCompany.RSAPublicKey;
import persistence.HSQLTableMessages;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigInteger;

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
                        File rsaFile = new File(Configuration.instance.keyfileDirectory + keyfileName +".json");
                        RSAPublicKey rsaPublicKey = getPublicKeyRsa(rsaFile);
                        channel.send(encrpytMassage,algorithmsTyp,rsaPublicKey,participantTarget,keyfileName,participantFrom);
                        HSQLTableMessages.instance.insertDataTableMessages(participantFrom.getId(),participantTarget.getId(),message,algo.toLowerCase(),encrpytMassage,keyfileName);


                       break;
                    case SHIFT:
                        channel.send(encrpytMassage,algorithmsTyp,participantTarget,keyfileName,participantFrom);
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

    private RSAPublicKey getPublicKeyRsa (File keyfile) {
        try
        {

            BufferedReader reader = new BufferedReader(new FileReader(keyfile));
            String currentLine;
            String stringN = "", stringD = "", stringE = "";

            while ((currentLine = reader.readLine()) != null) {
                if (currentLine.charAt(0) != '{' && currentLine.charAt(0) != '}')
                {
                    String[] splitted = currentLine.split(":");
                    if (splitted[1].charAt(splitted[1].length()-1) == ',') {
                        splitted[1] = splitted[1].substring(0, splitted[1].length()-1);
                    }


                    if (splitted[0].contains("e"))
                    {
                        stringE = splitted[1].trim();
                    }
                    if (splitted[0].contains("n"))
                    {
                        stringN = splitted[1].trim();
                    }
                }
            }

            BigInteger e = new BigInteger(stringE);
            BigInteger n = new BigInteger(stringN);

            RSAPublicKey rsaPublicKey = new RSAPublicKey(n,e);
            return  rsaPublicKey;

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return  null;
        }
    }
}
