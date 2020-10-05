package instructionParser;


import gui.GUI;
import instructionParser.parser.*;

public class InstructionExecute implements  IInstructionExecute{
    private ParserInstruction startParser;
    private GUI gui;
    public InstructionExecute(GUI gui) {
        ParserInstruction showAlgo = new ShowAlgo();
        ParserInstruction encryptMes = new EncryptMessage(showAlgo);
        ParserInstruction decryptMes = new DecryptMessage(encryptMes);
        ParserInstruction crackEncrypMes = new CrackEncrptMessage(decryptMes);
        ParserInstruction registerPart = new RegisterParticipant(crackEncrypMes);
        ParserInstruction creatChan = new CreateChannel(registerPart);
        ParserInstruction showChan = new ShowChannel(creatChan);
        ParserInstruction dropChan = new DropChannel(showChan);
        ParserInstruction instChan = new InstrudeChannel(dropChan);
        ParserInstruction sendMes = new SendMessage(instChan);

        startParser = sendMes;
        this.gui = gui;
    }

    public void instructionExecute(String instrucionString){

        startParser.parse(instrucionString, gui);
    }
}
