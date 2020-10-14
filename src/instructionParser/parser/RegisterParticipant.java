package instructionParser.parser;

import gui.GUI;
import instructionParser.ParserInstruction;
import networkCompany.CompanyNetControlCenter;
import networkCompany.ParticipantTyp;
import persistence.HSQLTableParticipants;
import persistence.HSQLTablePostboxs;

public class RegisterParticipant extends ParserInstruction {

    public RegisterParticipant(ParserInstruction successor) {
        this.setSuccessor(successor);
    }

    public void parse(String commandLine, GUI gui) {
        String[] commandLineArray = commandLine.split(" ");
        if (commandLine.matches("register participant (.+) with type (.+)") && commandLineArray.length == 6){
            String partTyp = commandLineArray[5];
            if (partTyp.equals("normal") || partTyp.equals("intruder")){
                //gui.writeTextAreaGui("Instruction register participant");
                String particName = commandLineArray[2];

                //CompanyControlCenter
                if (CompanyNetControlCenter.instance.isParticipantExist(particName)) {
                    gui.writeTextAreaGui("participant " + particName + " already exists, using existing postbox_[" + particName + "]");
                }else {
                    //DB
                    int idParticipant = HSQLTableParticipants.instance.insertDataTableParticipants(particName, ParticipantTyp.valueOf(partTyp));
                    HSQLTablePostboxs.instance.createTablePostbox(particName);
                    // CompanyControl
                    CompanyNetControlCenter.instance.createAndAddNewPatricipant(idParticipant,particName, ParticipantTyp.valueOf(partTyp));
                    gui.writeTextAreaGui("participant "+particName +" with type "+partTyp +" registered and postbox_["+particName+"] created");
                }



            }else {
                gui.writeTextAreaGui("Instruction register participant, but wrong participantType. Please choose only normal/intruder");
            }

        }else{
            super.parse(commandLine,gui);
        }

    }
}
