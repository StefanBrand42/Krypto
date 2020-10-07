package instructionParser.parser;

import configuration.Configuration;
import gui.GUI;
import instructionParser.ParserInstruction;

import java.util.List;

public class ShowAlgo extends ParserInstruction {
    @Override
    public void parse(String commandLine, GUI gui) {

        if (commandLine.equals("show algorithm")){
            //gui.writeTextAreaGui("Instruction Show Algo");
            List<String> list = Configuration.instance.getAlgorithmFileNames();
            String output= "";
            for (String algo: list) {
                // Without crackers
                if (algo.endsWith("cracker")) continue;
                if(!output.isBlank()) output += " | ";
                output += algo;
                //logger.log("found algo: "+algo);
            }
            gui.writeTextAreaGui(output);

        }else{
            super.parse(commandLine,gui);
        }

    }



}
