package instructionParser;

import gui.GUI;

public class ParserInstruction {
    private ParserInstruction sucessor;
    public void parse (String commandLine, GUI gui){
        if (getSucessor()!= null){
            getSucessor().parse(commandLine, gui);
        }else{
            gui.writeTextAreaGui("unable to find the correct Instruction parser");
        }
    }

    public ParserInstruction getSucessor() {
        return sucessor;
    }

    public void setSucessor(ParserInstruction sucessor) {
        this.sucessor = sucessor;
    }

}
