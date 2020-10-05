package instructionParser.parser;

import gui.GUI;
import instructionParser.ParserInstruction;

public class DecryptMessage extends ParserInstruction {
    public DecryptMessage(ParserInstruction sucessor) {
        this.setSucessor(sucessor);
    }

    public void parse(String commandLine, GUI gui) {
        String[] commandLineArray = commandLine.split(" ");
        if (commandLine.matches("decrypt message (.+) using (.+) and keyfile (.+)") && commandLineArray.length == 8){
            gui.writeTextAreaGui("Instruction decrypt message");
        }else{
            super.parse(commandLine,gui);
        }

    }
}
