package instructionParser.parser;

import gui.GUI;
import instructionParser.ParserInstruction;
import networkCampany.CompanyNetControlCenter;
import networkCampany.IParticipant;
import networkCampany.ParticipantTyp;
import persistence.HSQLTableChannel;

public class CreateChannel extends ParserInstruction {
    public CreateChannel(ParserInstruction successor) {
        this.setSuccessor(successor);
    }

    public void parse(String commandLine, GUI gui) {
        String[] commandLineArray = commandLine.split(" ");
        if (commandLine.matches("create channel (.+) from (.+) to (.+)") && commandLineArray.length == 7){
            gui.writeTextAreaGui("Instruction create channel");
            String channelName =commandLineArray[2];
            String participant01Name = commandLineArray[4];
            String participant02Name = commandLineArray[6];
            StringBuilder writeGui = new StringBuilder();

            // First check if Participant exist
            boolean paricipantExits = true;
            if (!CompanyNetControlCenter.instance.isParticipantExist(participant01Name)){
                writeGui.append("Participant: " +participant01Name +"do not exist please register participant \n");
                paricipantExits = false;
            }
            if (!CompanyNetControlCenter.instance.isParticipantExist(participant02Name)){
                writeGui.append("Participant: " +participant02Name +"do not exist please register participant");
                paricipantExits = false;
            }
            if (paricipantExits){
                // check Channel with Name Exist
                boolean creatChannel = true;
                if (CompanyNetControlCenter.instance.isChannelExist(channelName)){
                    writeGui.append("channel " +channelName +" already exists \n");
                    creatChannel = false;
                }
                //check if Communication between participant exits
                if (CompanyNetControlCenter.instance.isChannelExist(participant01Name,participant02Name)){
                    writeGui.append("communication channel between "+participant01Name +" and " +participant02Name+ " allready exist \n");
                    creatChannel = false;
                }
                // check if name Participant is same
                if (participant01Name.equals(participant02Name)) {
                    writeGui.append(participant01Name +" and "+participant02Name +" are identifical - cannot create Channel on itself \n ");
                    creatChannel = false;
                }
                if (creatChannel){
                    IParticipant participant01 = CompanyNetControlCenter.instance.getParticipantByName(participant01Name);
                    IParticipant participant02 = CompanyNetControlCenter.instance.getParticipantByName(participant02Name);
                    boolean typNormalBooth = true;
                    // check if participant Type is normal
                    if (participant01.getParticipantTyp()!= ParticipantTyp.normal){
                        writeGui.append("Participant: " +participant01.getName() +" is not form Typ normal");
                        typNormalBooth = false;
                    }
                    if (participant02.getParticipantTyp() != ParticipantTyp.normal){
                        writeGui.append("Participant: " +participant02.getName() +" is not form Typ normal");
                        typNormalBooth = false;
                    }
                    if (typNormalBooth){
                        CompanyNetControlCenter.instance.createAndAddNewChannel(channelName,participant01,participant02);
                        HSQLTableChannel.instance.insertDataTableChannel(channelName,participant01Name,participant02Name);
                        writeGui.append("channel "+channelName + " from "+participant01Name +" to "+participant02Name +" created");
                    }

                }

            }

            gui.writeTextAreaGui(writeGui.toString());

        }else{
            super.parse(commandLine,gui);
        }

    }
}
