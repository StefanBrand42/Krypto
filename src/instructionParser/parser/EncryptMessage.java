package instructionParser.parser;

import configuration.Configuration;
import configuration.CryptoCreator;
import gui.GUI;
import instructionParser.ParserInstruction;

import java.io.File;

public class EncryptMessage extends ParserInstruction {

    public EncryptMessage(ParserInstruction sucessor) {
        this.setSucessor(sucessor);
    }

    public void parse(String commandLine, GUI gui) {
        CryptoCreator creator = new CryptoCreator();

        String[] commandLineArray = commandLine.split(" ");
        if (commandLine.matches("encrypt message (.+) using (.+) and keyfile (.+)") && commandLineArray.length == 8){
            gui.writeTextAreaGui("Instruction encrypt message");
/*// von Jones
            if (!setAlgorithm(algorithm))
            {
                return null;
            }
            log("Creating encryption method at runtime from component");
            createCryptoMethod("encrypt");

            log("Detected encryption algorithm '" + Configuration.instance.algorithm + "'");
            String encryptedMessage = crypt(message, new File(Configuration.instance.keyfilesDirectory + keyfile));
            if (!encryptedMessage.equals("")) {
                log("Successfully encrypted message '" + message + "' to '" + encryptedMessage + "'");
            }
            return encryptedMessage;
// von Jones*/
/* von David
            // Load component
            //logger.log("Load cryptography variant for algorithm " + getParam("algorithm").toLowerCase());
            creator.createCryptoMethod(Algorithms(getParam("algorithm")), CryptoVar.ENCRYPT);
            Method method = creator.getCryptoVar();
            // Build file
            //logger.log("Get file object");
            String fileName = getParam("keyfile");
            if (!fileName.toLowerCase().endsWith(".json"))
                fileName += ".json";
            File file = new File(Configuration.instance.keyfileDirectory + fileName);
            // Check if file exists
            if (!file.exists()) {
                String msg = "Error: File '" + getParam("keyfile") + "' not found.";
            //    logger.log(msg);
            //    logger.log("Canceled EncryptMessageCommand");
                gui.writeTextAreaGui(msg);
                return;
            }

            // Encrypt message
            //logger.log("Executing encryption logic");
            String encrypted = (String) method.invoke(creator.getPort(), getParam("message"), file);
            //logger.log("Message '" + getParam("message") + "' encrypted. Result of encryption: " + encrypted);
            gui.writeTextAreaGui(encrypted);
            //logger.log("Executed EncryptMessageCommand");
*/
        }else{
            super.parse(commandLine,gui);
        }

    }
}
