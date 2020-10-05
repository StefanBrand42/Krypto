package instructionParser.parser;

import gui.GUI;
import instructionParser.ParserInstruction;

public class EncryptMessage  extends ParserInstruction {

    public EncryptMessage(ParserInstruction sucessor) {
        this.setSucessor(sucessor);
    }

    public void parse(String commandLine, GUI gui) {
        String[] commandLineArray = commandLine.split(" ");
        if (commandLine.matches("encrypt message (.+) using (.+) and keyfile (.+)") && commandLineArray.length == 8){
            gui.writeTextAreaGui("Instruction encrypt message");
        }else{
            super.parse(commandLine,gui);
        }

    }
}
