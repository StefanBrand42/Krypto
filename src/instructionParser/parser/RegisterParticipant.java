package instructionParser.parser;

import gui.GUI;
import instructionParser.ParserInstruction;
import persistence.HSQLDB;
import persistence.ParticipantTyp;

public class RegisterParticipant extends ParserInstruction {

    public RegisterParticipant(ParserInstruction sucessor) {
        this.setSucessor(sucessor);
    }

    public void parse(String commandLine, GUI gui) {
        String[] commandLineArray = commandLine.split(" ");
        if (commandLine.matches("register participant (.+) with type (.+)") && commandLineArray.length == 6){
            String partTyp = commandLineArray[5];
            if (partTyp.equals("normal") || partTyp.equals("intruder")){
                gui.writeTextAreaGui("Instruction register participant");
                String particName = commandLineArray[2];
                if (HSQLDB.instance.participantExist(particName)) {
                    gui.writeTextAreaGui("participant " + particName + " already exists, using existing postbox_[" + particName + "]");
                }else {
                    HSQLDB.instance.insertDataTableParticipants(particName, ParticipantTyp.valueOf(partTyp));
                    HSQLDB.instance.createTablePostbox(particName);
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
