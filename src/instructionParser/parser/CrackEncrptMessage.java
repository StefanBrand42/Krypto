package instructionParser.parser;

import gui.GUI;
import instructionParser.ParserInstruction;

public class CrackEncrptMessage  extends ParserInstruction {

    public CrackEncrptMessage(ParserInstruction sucessor) {
        this.setSucessor(sucessor);
    }

    public void parse(String commandLine, GUI gui) {
        String[] commandLineArray = commandLine.split(" ");
        if (commandLine.matches("crack encrypted message (.+) using (.+)") && commandLineArray.length == 6){
            gui.writeTextAreaGui("Instruction crack encrypted message");
        }else{
            super.parse(commandLine,gui);
        }

    }
}
