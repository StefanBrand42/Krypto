package instructionParser.parser;

import configuration.Configuration;
import crypto.CryptoCreator;
import gui.GUI;
import instructionParser.ParserInstruction;

public class EncryptMessage extends ParserInstruction {

    public EncryptMessage(ParserInstruction sucessor) {
        this.setSucessor(sucessor);
    }
    CryptoCreator creator = new CryptoCreator();

    public void parse(String commandLine, GUI gui) {

        // Zum Testen:  encrypt message "test" using rsa and keyfile rsa_key1
        //              encrypt message "test" using shift and keyfile shift_key
        String[] commandLineArray = commandLine.split(" ");
        if (commandLine.matches("encrypt message \"(.+)\" using (.+) and keyfile (.+)") && commandLineArray.length == 8){
            gui.writeTextAreaGui("Instruction encrypt message");
            String message1 = commandLineArray[2]; // muss in "" sein
            String message = message1.replace("\"","");
            String algo = commandLineArray[4];
            String key = commandLineArray[7];

            // Check if algo and KeyFile exist in Components
            boolean readyforEncrypt = true  ;
            StringBuilder stringBuilder01 = new StringBuilder();
            if (!Configuration.instance.checkIfAlgoExist(algo)){
                stringBuilder01.append("Your chosen algorithm dos not exit \n");
                readyforEncrypt = false;
            }

            if (!Configuration.instance.checkIfKeyFileNameExist(key)){
                stringBuilder01.append("Your chosen keyfilename dos not exit (please without .json) \n");

                readyforEncrypt = false;
            }

            if (readyforEncrypt)
            {
                gui.writeTextAreaGui(gui.getCryptoCreator().encryptMessage(message,algo,key));
            }else {
                gui.writeTextAreaGui(stringBuilder01.toString());
            }


        }else{
            super.parse(commandLine,gui);
        }

    }

    /*
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
                Configuration.instance.algorithmsTyp = AlgorithmsTyp.RSA;
                break;
            case "shift":
                Configuration.instance.algorithmsTyp = AlgorithmsTyp.SHIFT;
                break;
            default:
                return false;
        }
        return true;
    }
    */

}
