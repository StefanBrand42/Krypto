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
        ParserInstruction crackEncrypMes = new CrackEncryptedMessage(decryptMes);
        ParserInstruction registerPart = new RegisterParticipant(crackEncrypMes);
        ParserInstruction creatChan = new CreateChannel(registerPart);
        ParserInstruction showChan = new ShowChannel(creatChan);
        ParserInstruction dropChan = new DropChannel(showChan);
        ParserInstruction instChan = new IntrudeChannel(dropChan);
        ParserInstruction sendMes = new SendMessage(instChan);
        ParserInstruction help = new Help(sendMes);

        startParser = help;
        this.gui = gui;
    }

    public void instructionExecute(String instrucionString){

        startParser.parse(instrucionString, gui);
    }
}
