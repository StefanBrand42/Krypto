package instructionParser.parser;

import configuration.Configuration;
import crypto.AlgorithmsTyp;
import crypto.CryptoCreator;
import gui.GUI;
import instructionParser.ParserInstruction;
import crypto.Cracker;
import crypto.RSAPublicKey;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class CrackEncryptedMessage extends ParserInstruction {

    public CrackEncryptedMessage(ParserInstruction successor) {
        this.setSuccessor(successor);
    }

    CryptoCreator creator = new CryptoCreator();

    public void parse(String commandLine, GUI gui) {
        // Zum Testen morpheus: crack encrypted message "rtwumjzx" using shift
        //                      crack encrypted message "JbkPFt+y+j8=" using rsa and keyfile rsa_key3
        String[] commandLineArray = commandLine.split(" ");
        if (commandLine.matches("crack encrypted message \"(.+)\" using (.+)") && commandLineArray.length == 6 ||
                commandLine.matches("crack encrypted message \"(.+)\" using (.+) and keyfile (.+)") && commandLineArray.length == 9){ // oder für RSA
            //gui.writeTextAreaGui("Instruction crack encrypted message");
            String message1 = commandLineArray[3]; // muss in "" sein
            String message = message1.replace("\"","");
            String algo = commandLineArray[5];
            AlgorithmsTyp algotyp = creator.getAlgoTypFromName(algo);
            File publicKeyFile = null;
            String publicKey1 = "noKey";
            RSAPublicKey rsaPublicKey= null;

            if(algotyp.equals(AlgorithmsTyp.RSA) && commandLineArray.length == 9) {
                publicKey1 = commandLineArray[8];
                publicKeyFile = new File (Configuration.instance.keyfileDirectory + publicKey1 +".json");
                try {
                    rsaPublicKey = getPublicKeyFromFile(publicKeyFile);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }

            if (algotyp.equals(AlgorithmsTyp.RSA) && commandLineArray.length < 9) {
                gui.writeTextAreaGui("PublicKey is missing. (RSA needs the PublicKey)");
                return;
            }

            // Check if Algo exists in Components
            boolean readyForCracking = true  ;
            StringBuilder stringBuilder01 = new StringBuilder();
            if (!Configuration.instance.checkIfAlgoExist(algo)){
                stringBuilder01.append("Your chosen algorithm does not exist. \n");
                readyForCracking = false;
            }

            if (readyForCracking)
            {
                Future<String> future = new Cracker().cracking(algotyp, message, rsaPublicKey);

                try {
                    String decryptMessage = future.get(Configuration.instance.TimeoutTimeSeconds, TimeUnit.SECONDS);
                    gui.writeTextAreaGui(decryptMessage);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    gui.writeTextAreaGui("cracking encrypted \""+message+"\" failed");
                    future.cancel(true);
                }


            }else {
                gui.writeTextAreaGui(stringBuilder01.toString());
            }
        }else{
            super.parse(commandLine,gui);
        }

    }


    private RSAPublicKey getPublicKeyFromFile(File publicKey) throws FileNotFoundException {
        BigInteger n = null;
        BigInteger e = null;
        Scanner scanner = new Scanner(publicKey);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.contains("\"n\":")) {
                n = getParam(line);
            }
            else if (line.contains("\"e\":")) {
                e = getParam(line);
            }
        }
        RSAPublicKey rsaPublicKey = new RSAPublicKey(n,e);
        return rsaPublicKey;
    }

    private BigInteger getParam(String input) {
        String[] lineParts = input.split(":");
        String line = lineParts[1];
        line = line.replace(",", "").trim();
        return new BigInteger(line);
    }
}
