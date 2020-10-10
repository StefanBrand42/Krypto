package instructionParser.parser;

import configuration.Algorithms;
import configuration.Configuration;
import configuration.CryptoCreator;
import gui.GUI;
import instructionParser.ParserInstruction;

import java.io.File;

public class EncryptMessage extends ParserInstruction {

    public EncryptMessage(ParserInstruction sucessor) {
        this.setSucessor(sucessor);
    }
    CryptoCreator creator = new CryptoCreator();

    public void parse(String commandLine, GUI gui) {

        // Zum Testen:  encrypt message "test" using rsa and keyfile rsa_key1.json
        //              encrypt message "test" using shift and keyfile shift_key.json
        String[] commandLineArray = commandLine.split(" ");
        if (commandLine.matches("encrypt message \"(.+)\" using (.+) and keyfile (.+)") && commandLineArray.length == 8){
            gui.writeTextAreaGui("Instruction encrypt message");
            String message1 = commandLineArray[2]; // muss in "" sein
            String message = message1.replace("\"","");
            String algo = commandLineArray[4];
            String key = commandLineArray[7];

            gui.writeTextAreaGui(encrypt(message,algo,key));
        }else{
            super.parse(commandLine,gui);
        }

    }

    public String encrypt(String message, String algo, String key)
    {
        if (!chooseAlgorithm(algo))
        {
            return null;
        }
        //log("Creating encryption method at runtime from component");
        creator.createCryptoMethod("encrypt");

        //log("Detected encryption algorithm '" + algo + "'");
        String encryptedMessage = creator.cryption(message, new File(Configuration.instance.keyfileDirectory + key));
        if (!encryptedMessage.equals("")) {
            //log("Successfully encrypted message '" + message + "' to '" + encryptedMessage + "'");
        }
        return encryptedMessage;
    }


    private boolean chooseAlgorithm(String algorithm)
    {
        switch (algorithm.toLowerCase()) {
            case "rsa":
                Configuration.instance.algorithms = Algorithms.RSA;
                break;
            case "shift":
                Configuration.instance.algorithms = Algorithms.SHIFT;
                break;
            default:
                return false;
        }
        return true;
    }
}
