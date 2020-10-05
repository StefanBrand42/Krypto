package instructionParser.parser;

import gui.GUI;
import instructionParser.ParserInstruction;
import persistence.HSQLDB;
import persistence.ShowChanelDataBase;

import java.util.ArrayList;

public class ShowChannel  extends ParserInstruction {
    public ShowChannel(ParserInstruction sucessor) {
        this.setSucessor(sucessor);
    }

    public void parse(String commandLine, GUI gui) {

        if (commandLine.equals("show channel")){
            gui.writeTextAreaGui("Instruction show channel");
            ArrayList<ShowChanelDataBase>  showChanelDataBaseArrayList= HSQLDB.instance.showChannels();
            StringBuilder stringBuilder01 = new StringBuilder();
            for (ShowChanelDataBase showChanelData: showChanelDataBaseArrayList) {
               stringBuilder01.append(showChanelData.getNameChanel() +" | "+ showChanelData.getNameParticipant01()+" and "+showChanelData.getNameParticipant02()+"\n") ;
            }
            gui.writeTextAreaGui(stringBuilder01.toString());
        }else{
            super.parse(commandLine,gui);
        }

    }
}
