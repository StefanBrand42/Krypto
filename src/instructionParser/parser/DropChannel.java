package instructionParser.parser;

import gui.GUI;
import instructionParser.ParserInstruction;
import networkCompany.CompanyNetControlCenter;
import persistence.HSQLTableChannel;

public class DropChannel extends ParserInstruction {

    public DropChannel(ParserInstruction successor) {
        this.setSuccessor(successor);
    }

    public void parse(String commandLine, GUI gui) {
        String[] commandLineArray = commandLine.split(" ");
        if (commandLine.matches("drop channel (.+)") && commandLineArray.length == 3){
            gui.writeTextAreaGui("Instruction drop channel");
            String channelName = commandLineArray[2];
            Boolean succesDB = HSQLTableChannel.instance.dropOneChanel(channelName);
            Boolean succesComNet = CompanyNetControlCenter.instance.delateChannel(channelName);
            if (succesComNet || succesDB){
                gui.writeTextAreaGui("channel "+ commandLineArray[2]+ " deleted");
            }else {
                gui.writeTextAreaGui("unkown channel " +commandLineArray[2]);
            }
        }else{
            super.parse(commandLine,gui);
        }

    }
}
