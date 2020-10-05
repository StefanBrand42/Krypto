package instructionParser.parser;

import gui.GUI;
import instructionParser.ParserInstruction;

public class InstrudeChannel extends  ParserInstruction {
    public InstrudeChannel(ParserInstruction sucessor) {
        this.setSucessor(sucessor);
    }

    public void parse(String commandLine, GUI gui) {
        String[] commandLineArray = commandLine.split(" ");
        if (commandLine.matches("instrude channel (.+) by (.+)") && commandLineArray.length == 5){
            gui.writeTextAreaGui("Instruction instrude channel");
        }else{
            super.parse(commandLine,gui);
        }

    }
}
