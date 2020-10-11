package instructionParser.parser;

import gui.GUI;
import instructionParser.ParserInstruction;

public class IntrudeChannel extends  ParserInstruction {
    public IntrudeChannel(ParserInstruction successor) {
        this.setSuccessor(successor);
    }

    public void parse(String commandLine, GUI gui) {
        String[] commandLineArray = commandLine.split(" ");
        if (commandLine.matches("intrude channel (.+) by (.+)") && commandLineArray.length == 5){
            gui.writeTextAreaGui("Instruction intrude channel");
        }else{
            super.parse(commandLine,gui);
        }

    }
}
