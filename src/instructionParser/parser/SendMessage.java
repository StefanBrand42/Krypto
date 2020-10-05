package instructionParser.parser;

import gui.GUI;
import instructionParser.ParserInstruction;

public class SendMessage extends  ParserInstruction {

    public SendMessage(ParserInstruction sucessor) {
        this.setSucessor(sucessor);
    }

    public void parse(String commandLine, GUI gui) {
        String[] commandLineArray = commandLine.split(" ");
        if (commandLine.matches("send message (.+) from (.+) to (.+) using (.+) and keyfile (.+)") && commandLineArray.length == 12){
            gui.writeTextAreaGui("Instruction send message");
        }else{
            super.parse(commandLine,gui);
        }

    }
}
