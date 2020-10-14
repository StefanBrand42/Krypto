package instructionParser;

import gui.GUI;

import java.io.FileNotFoundException;

public abstract class ParserInstruction {
    private ParserInstruction successor;
    public void parse (String commandLine, GUI gui)  {
        if (getSuccessor()!= null){
            getSuccessor().parse(commandLine, gui);
        }else{
            gui.writeTextAreaGui("Unable to find the correct Instruction parser");
        }
    }

    public ParserInstruction getSuccessor() {
        return successor;
    }

    public void setSuccessor(ParserInstruction successor) {
        this.successor = successor;
    }

}
