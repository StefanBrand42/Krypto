package instructionParser.parser;

import gui.GUI;
import instructionParser.ParserInstruction;

public class SendMessage extends  ParserInstruction {

    public SendMessage(ParserInstruction successor) {
        this.setSuccessor(successor);
    }

    public void parse(String commandLine, GUI gui) {
        String[] commandLineArray = commandLine.split(" ");
        if (commandLine.matches("send message  \"(.+)\" from (.+) to (.+) using (.+) and keyfile (.+)") && commandLineArray.length == 12){
            gui.writeTextAreaGui("Instruction send message");
            String message = commandLineArray[2];
            String participant01 = commandLineArray[4];
            String participant02 = commandLineArray[6];
            String algo = commandLineArray[8];
            String keyfileName = commandLineArray[11];


        }else{

            super.parse(commandLine,gui);
        }


    }
}
