package instructionParser.parser;

import configuration.Configuration;
import crypto.AlgorithmsTyp;
import crypto.CryptoCreator;
import gui.GUI;
import instructionParser.ParserInstruction;
import networkCompany.Cracker;

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
        // Zum Testen:  crack encrypted message "yjxy" using shift
        //              crack encrypted message "JbkPFt+y+j8=" using rsa and keyfile rsa_key3
        String[] commandLineArray = commandLine.split(" ");
        if (commandLine.matches("crack encrypted message \"(.+)\" using (.+)") && commandLineArray.length == 6 ||
                commandLine.matches("crack encrypted message \"(.+)\" using (.+) and keyfile (.+)") && commandLineArray.length == 9){ // oder für RSA
            //gui.writeTextAreaGui("Instruction crack encrypted message");
            String message1 = commandLineArray[3]; // muss in "" sein
            String message = message1.replace("\"","");
            String algo = commandLineArray[5];
            AlgorithmsTyp algotyp = creator.getAlgoTypFromName(algo);
            String publicKey = commandLineArray[8];

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
                //gui.writeTextAreaGui(creator.cracking(message,algotyp));


                Future<String> future = new Cracker().cracking(message,algotyp, publicKey);

                try {
                    String decryptMessage = future.get(30, TimeUnit.SECONDS);
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
}
