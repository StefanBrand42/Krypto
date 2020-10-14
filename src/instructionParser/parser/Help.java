package instructionParser.parser;

import gui.GUI;
import instructionParser.ParserInstruction;
import networkCompany.CompanyNetControlCenter;
import persistence.HSQLTableChannel;

public class Help extends ParserInstruction {
    public Help(ParserInstruction successor) {
        this.setSuccessor(successor);
    }

    public void parse(String commandLine, GUI gui) {

        if (commandLine.equals("?")){
            gui.writeTextAreaGui("help");


        }else{
            super.parse(commandLine,gui);
        }

    }
}
