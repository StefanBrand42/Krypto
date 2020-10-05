package instructionParser.parser;

import gui.GUI;
import instructionParser.ParserInstruction;
import persistence.HSQLDB;

import java.util.ArrayList;

public class ShowAlgo  extends ParserInstruction {
    @Override
    public void parse(String commandLine, GUI gui) {

        if (commandLine.equals("show algorithm")){
            gui.writeTextAreaGui("Instruction Show Algo");

        }else{
            super.parse(commandLine,gui);
        }

    }
}
