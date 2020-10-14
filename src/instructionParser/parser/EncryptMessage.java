package instructionParser.parser;

import configuration.Configuration;
import crypto.CryptoCreator;
import gui.GUI;
import instructionParser.ParserInstruction;
import logging.Logging;

public class EncryptMessage extends ParserInstruction {

    public EncryptMessage(ParserInstruction successor) {
        this.setSuccessor(successor);
    }


    public void parse(String commandLine, GUI gui) {

        // Zum Testen:  encrypt message "morpheus" using rsa and keyfile rsa_key1
        //              encrypt message "morpheus" using shift and keyfile shift_key
        String[] commandLineArray = commandLine.split(" ");
        if (commandLine.matches("encrypt message \"(.+)\" using (.+) and keyfile (.+)") && commandLineArray.length == 8){
            //gui.writeTextAreaGui("Instruction encrypt message");
            String message1 = commandLineArray[2]; // muss in "" sein
            String message = message1.replace("\"","");
            String algo = commandLineArray[4];
            String key = commandLineArray[7];




            // Check if Algo and KeyFile exist in Components
            boolean readyforEncrypt = true  ;
            StringBuilder stringBuilder01 = new StringBuilder();
            if (!Configuration.instance.checkIfAlgoExist(algo)){
                stringBuilder01.append("Your chosen algorithm does not exist. \n");
                readyforEncrypt = false;
            }

            if (!Configuration.instance.checkIfKeyFileNameExist(key)){
                stringBuilder01.append("Your chosen keyfile does not exist. (please write it without .json) \n");

                readyforEncrypt = false;
            }

            if (readyforEncrypt)
            {
                if (Configuration.instance.debug){
                    gui.getCryptoCreator().setLogging(new Logging("encrypt",algo));
                }

                gui.writeTextAreaGui(gui.getCryptoCreator().encryptMessage(message,algo,key));
            }else {
                gui.writeTextAreaGui(stringBuilder01.toString());
            }


        }else{
            super.parse(commandLine,gui);
        }

    }
}
