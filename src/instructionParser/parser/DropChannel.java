package instructionParser.parser;

import gui.GUI;
import instructionParser.ParserInstruction;
import persistence.HSQLDB;

public class DropChannel extends ParserInstruction {

    public DropChannel(ParserInstruction sucessor) {
        this.setSucessor(sucessor);
    }

    public void parse(String commandLine, GUI gui) {
        String[] commandLineArray = commandLine.split(" ");
        if (commandLine.matches("drop channel (.+)") && commandLineArray.length == 3){
            gui.writeTextAreaGui("Instruction drop channel");
            Boolean succes = HSQLDB.instance.dropOneChanel(commandLineArray[2]);
            if (succes){
                gui.writeTextAreaGui("channel "+ commandLineArray[2]+ " deleted");
            }else {
                gui.writeTextAreaGui("unkown channel " +commandLineArray[2]);
            }
        }else{
            super.parse(commandLine,gui);
        }

    }
}
