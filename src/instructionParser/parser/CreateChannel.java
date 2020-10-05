package instructionParser.parser;

import gui.GUI;
import instructionParser.ParserInstruction;

public class CreateChannel extends ParserInstruction {
    public CreateChannel(ParserInstruction sucessor) {
        this.setSucessor(sucessor);
    }

    public void parse(String commandLine, GUI gui) {
        String[] commandLineArray = commandLine.split(" ");
        if (commandLine.matches("create channel (.+) from (.+) to (.+)") && commandLineArray.length == 7){
            gui.writeTextAreaGui("Instruction create channel");
        }else{
            super.parse(commandLine,gui);
        }

    }
}
