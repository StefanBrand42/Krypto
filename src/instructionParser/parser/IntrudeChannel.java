package instructionParser.parser;

import gui.GUI;
import instructionParser.ParserInstruction;
import networkCompany.*;

public class IntrudeChannel extends  ParserInstruction {
    public IntrudeChannel(ParserInstruction successor) {
        this.setSuccessor(successor);
    }

    public void parse(String commandLine, GUI gui) {
        String[] commandLineArray = commandLine.split(" ");
        if (commandLine.matches("intrude channel (.+) by (.+)") && commandLineArray.length == 5){
            //gui.writeTextAreaGui("Instruction intrude channel");
            String channelName = commandLineArray[2];
            String participantIn = commandLineArray[4];
            StringBuilder stringBuilder01 = new StringBuilder();
            boolean checkInputNames = true;
            // check if channel exist
            if (!CompanyNetControlCenter.instance.isChannelExist(channelName)){
                stringBuilder01.append("The channel does not exist \n");
                checkInputNames = false;
            }
            if (!CompanyNetControlCenter.instance.isParticipantExist(participantIn)){
                stringBuilder01.append("The participant does not exist \n");
                checkInputNames = false;

            }else if (!(CompanyNetControlCenter.instance.getParticipantByName(participantIn).getParticipantTyp() == ParticipantTyp.intruder)){
                stringBuilder01.append("The participant ist not from typ intruder \n");
                checkInputNames = false;
            }
            if (checkInputNames){
                IParticipant participant = CompanyNetControlCenter.instance.getParticipantByName(participantIn);
                IChannel channel = CompanyNetControlCenter.instance.getChannelByName(channelName);
                IParticipantIntruderListener participantIntruderListener = participant.getParticipantIntruder();
                // add listener
                channel.addListener(participantIntruderListener);
                gui.writeTextAreaGui("The participant is registrate to the channel \n");

            }else {
                gui.writeTextAreaGui(stringBuilder01.toString());
            }

        }else{
            super.parse(commandLine,gui);
        }

    }
}
