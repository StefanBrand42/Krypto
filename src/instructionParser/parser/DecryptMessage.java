package instructionParser.parser;

import crypto.CryptoCreator;
import gui.GUI;
import instructionParser.ParserInstruction;

public class DecryptMessage extends ParserInstruction {
    public DecryptMessage(ParserInstruction successor) {
        this.setSuccessor(successor);
    }
    CryptoCreator creator = new CryptoCreator();

    public void parse(String commandLine, GUI gui) {

        // Zum Testen:  decrypt message "SuvEVMVtNhg=" using rsa and keyfile rsa_key1
        //              decrypt message "yjxy" using shift and keyfile shift_key
        String[] commandLineArray = commandLine.split(" ");
        if (commandLine.matches("decrypt message \"(.+)\" using (.+) and keyfile (.+)") && commandLineArray.length == 8){
            //gui.writeTextAreaGui("Instruction decrypt message");
                String message1 = commandLineArray[2]; // muss in "" sein
                String message = message1.replace("\"","");
                String algo = commandLineArray[4];
                String key = commandLineArray[7];

                gui.writeTextAreaGui(gui.getCryptoCreator().decryptMesagge(message,algo,key));
        }else{
            super.parse(commandLine,gui);
        }
    }

}