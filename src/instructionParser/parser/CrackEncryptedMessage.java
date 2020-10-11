package instructionParser.parser;

import configuration.Configuration;
import crypto.AlgorithmsTyp;
import crypto.CryptoCreator;
import gui.GUI;
import instructionParser.ParserInstruction;

public class CrackEncryptedMessage extends ParserInstruction {

    public CrackEncryptedMessage(ParserInstruction successor) {
        this.setSuccessor(successor);
    }

    CryptoCreator creator = new CryptoCreator();

    public void parse(String commandLine, GUI gui) {
        // Zum Testen: crack encrypted message "yjxy" using shift
        String[] commandLineArray = commandLine.split(" ");
        if (commandLine.matches("crack encrypted message \"(.+)\" using (.+)") && commandLineArray.length == 6){
            //gui.writeTextAreaGui("Instruction crack encrypted message");
            String message1 = commandLineArray[3]; // muss in "" sein
            String message = message1.replace("\"","");
            String algo = commandLineArray[5];
            AlgorithmsTyp algotyp = creator.getAlgoTypFromName(algo);

            // Check if Algo exists in Components
            boolean readyForCracking = true  ;
            StringBuilder stringBuilder01 = new StringBuilder();
            if (!Configuration.instance.checkIfAlgoExist(algo)){
                stringBuilder01.append("Your chosen algorithm does not exist. \n");
                readyForCracking = false;
            }

            if (readyForCracking)
            {
                gui.writeTextAreaGui(creator.cracking(message,algotyp));
            }else {
                gui.writeTextAreaGui(stringBuilder01.toString());
            }
        }else{
            super.parse(commandLine,gui);
        }

    }
}
