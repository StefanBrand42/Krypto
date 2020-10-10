package instructionParser.parser;

import configuration.Algorithms;
import configuration.Configuration;
import configuration.CryptoCreator;
import gui.GUI;
import instructionParser.ParserInstruction;

import java.io.File;

public class DecryptMessage extends ParserInstruction {
    public DecryptMessage(ParserInstruction sucessor) {
        this.setSucessor(sucessor);
    }
    CryptoCreator creator = new CryptoCreator();

    public void parse(String commandLine, GUI gui) {

        // Zum Testen:  decrypt message "SuvEVMVtNhg=" using rsa and keyfile rsa_key1.json
        //              decrypt message "yjxy" using shift and keyfile shift_key.json
        String[] commandLineArray = commandLine.split(" ");
        if (commandLine.matches("decrypt message \"(.+)\" using (.+) and keyfile (.+)") && commandLineArray.length == 8){
            gui.writeTextAreaGui("Instruction decrypt message");
                String message1 = commandLineArray[2]; // muss in "" sein
                String message = message1.replace("\"","");
                String algo = commandLineArray[4];
                String key = commandLineArray[7];

                gui.writeTextAreaGui(decrypt(message,algo,key));
        }else{
            super.parse(commandLine,gui);
        }
    }
    public String decrypt(String message, String algo, String key)
    {
        if (!chooseAlgorithm(algo))
        {
            return null;
        }
        //log("Creating decryption method at runtime from component");
        creator.createCryptoMethod("decrypt");

        //log("Detected decryption algorithm '" + algo + "'");
        String decryptedMessage = creator.cryption(message, new File(Configuration.instance.keyfileDirectory + key));
        if (!decryptedMessage.equals("")) {
            //log("Successfully decrypted message '" + message + "' to '" + decryptedMessage + "'");
        }
        return decryptedMessage;
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